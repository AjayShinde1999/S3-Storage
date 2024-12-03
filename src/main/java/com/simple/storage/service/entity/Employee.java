package com.simple.storage.service.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employees")
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String attachment;
}
