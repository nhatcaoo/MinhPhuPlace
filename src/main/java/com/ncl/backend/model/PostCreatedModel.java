package com.ncl.backend.model;

import com.ncl.backend.entity.Image;
import com.ncl.backend.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreatedModel {
    private Post post;
    private List<Image> list;
}
