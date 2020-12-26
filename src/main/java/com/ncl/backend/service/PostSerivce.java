package com.ncl.backend.service;

import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.PostCreatedModel;
import com.ncl.backend.model.ServiceResult;

public interface PostSerivce {
    ServiceResult getAllServicePost();
    ServiceResult getHomepageServicePost();
    ServiceResult getOnePost(Long id) throws NotFoundException;
    ServiceResult createPost(PostCreatedModel postCreatedModel);
    ServiceResult editPost(PostCreatedModel postCreatedModel) throws NotFoundException;
    ServiceResult getAllEvent();
    void initMajorPost();
    ServiceResult deletePost(Long id) throws NotFoundException;
}
