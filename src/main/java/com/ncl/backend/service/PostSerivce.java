package com.ncl.backend.service;

import com.ncl.backend.entity.Preface;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.exception.NullObjectException;
import com.ncl.backend.model.PostCreatedModel;
import com.ncl.backend.model.ServiceResult;

public interface PostSerivce {
    ServiceResult getAllServicePost();
    ServiceResult getHomepageServicePost();
    ServiceResult getHomepageServicePostOnePic();
    ServiceResult getOnePost(Long id) throws NotFoundException;
    ServiceResult createPost(PostCreatedModel postCreatedModel) throws NullObjectException;
    ServiceResult editPost(PostCreatedModel postCreatedModel) throws NotFoundException;
    ServiceResult getAllEvent();
    void initMajorPost();
    ServiceResult deletePost(Long id) throws NotFoundException;

    ServiceResult getAllMinorService();

    ServiceResult editPreface(Preface post) throws NotFoundException;

    ServiceResult getPreface();
}
