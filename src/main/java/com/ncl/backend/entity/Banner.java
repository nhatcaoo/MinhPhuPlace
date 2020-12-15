package com.ncl.backend.entity;

import com.ncl.backend.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    private Long id;

    private Image image;

    private String title;

    private String brief;
}
