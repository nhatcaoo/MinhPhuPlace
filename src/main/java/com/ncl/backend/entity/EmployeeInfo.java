package com.ncl.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employeeInfo")
public class EmployeeInfo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "type")
    private String type;
}
