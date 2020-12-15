package com.ncl.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "brief", nullable = true, columnDefinition = "TEXT")
    private String brief;

    @Column(name = "short_description", nullable = true, columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "description", nullable = true, length = 100000)
    private String description;

    @Column(name = "type", nullable = true)
    private String type;

}
