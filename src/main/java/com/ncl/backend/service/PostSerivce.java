package com.ncl.backend.service;

import com.ncl.backend.entity.Post;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;

public interface PostSerivce {
    ServiceResult getAllServicePost();
    ServiceResult getOnePost(Long id) throws NotFoundException;
    ServiceResult createPost(Post post);
    ServiceResult editPost(Post post) throws NotFoundException;

}
