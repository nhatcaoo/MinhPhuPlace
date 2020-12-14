package com.ncl.backend.model;

import com.ncl.backend.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostServiceDTO {
    private Long id;
    private String title;
    private String brief;
    private String shortDescription;
    private String type;
    private String coverImg;
    public PostServiceDTO(Post post, String coverImg){
        this.id = post.getId();
        this.brief = post.getBrief();
        this.shortDescription = post.getShortDescription();
        this.title = post.getTitle();
        this.type = post.getType();
        this.coverImg = coverImg;
    }
}
