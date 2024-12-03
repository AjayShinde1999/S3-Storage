package com.simple.storage.service.service.impl;

import com.simple.storage.service.entity.Employee;
import com.simple.storage.service.payload.EmployeeDto;
import com.simple.storage.service.repository.EmployeeRepository;
import com.simple.storage.service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private S3Client s3Client;

    @Value("${cloud.aws.bucket-name}")
    private String bucketName;

    @Override
    public Employee saveOneEmployee(EmployeeDto employeeDto) throws IOException {
        String attachment = uploadImageToS3Bucket(employeeDto);
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setAttachment(attachment);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    String uploadImageToS3Bucket(EmployeeDto employeeDto) throws IOException {
        String attachment = "attachments/" + employeeDto.getAttachment().getOriginalFilename();
        MultipartFile multipartFile = employeeDto.getAttachment();

        File tempFile = File.createTempFile("temp", null);
        try {
            multipartFile.transferTo(tempFile);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(attachment)
                    .build();

            s3Client.putObject(putObjectRequest, tempFile.toPath());
            URL url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(attachment).build());
            return url.toString();
        } finally {
            tempFile.delete();
        }


    }

    // without manually handling temp file
    public String uploadFile(MultipartFile file, String ticketId) throws IOException {
        String fileName = "attachments/" + file.getOriginalFilename(); // Construct the S3 key

        // Upload directly from MultipartFile's InputStream
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName) // The S3 key (file name)
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Generate the file's URL after upload
        URL url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName).build());

        return url.toString(); // Return the public URL of the uploaded file
    }
}




