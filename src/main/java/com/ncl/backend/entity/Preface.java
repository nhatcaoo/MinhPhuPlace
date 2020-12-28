package com.ncl.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "preface")
public class Preface {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "description", nullable = true, length = 100000)
    private String description;

}
