package com.ncl.backend.model;

import com.ncl.backend.entity.Post;
import com.ncl.backend.entity.PostImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreatedModel {
    private Post post;
    private List<PostImage> list;
}
