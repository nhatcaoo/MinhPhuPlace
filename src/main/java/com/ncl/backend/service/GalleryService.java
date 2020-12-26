package com.ncl.backend.service;

import com.ncl.backend.entity.Gallery;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;

public interface GalleryService {
    ServiceResult getAllPic();
    ServiceResult addPic(Gallery gallery);
    ServiceResult deletePic(Long id) throws NotFoundException;
}
