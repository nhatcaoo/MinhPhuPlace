package com.ncl.backend.entity;

import com.ncl.backend.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Entity
@Data
@Table(name = "banner")
public class Banner {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "image", nullable = false)
    private Image image;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "brief", nullable = true)
    private String brief;
}
