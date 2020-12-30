package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Gallery;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.GalleryRepository;
import com.ncl.backend.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class GalleryServiceImpl implements GalleryService {
    @Autowired
    GalleryRepository galleryRepository;
    @Override
    public ServiceResult getAllPic(Boolean isLimit) {
        int size = 6;
        if(!isLimit) size = Integer.MAX_VALUE;
        PageRequest pageRequest = PageRequest.of(0,size);
        return new ServiceResult(galleryRepository.findAll(pageRequest), ServiceResult.SUCCESS, Constant.EMPTY);

    }

    @Override
    public ServiceResult addPic(Gallery gallery) {
        if(galleryRepository.count()>6)
            return new ServiceResult(null, ServiceResult.FAIL, "Không thể thêm mới vì vượt quá số lượng ảnh cho phép");
        galleryRepository.save(gallery);
        return new ServiceResult(null, ServiceResult.SUCCESS, "Thêm ảnh thành công");
    }

    @Override
    public ServiceResult deletePic(Long id) throws NotFoundException {
        if(!galleryRepository.existsById(id))
            throw new NotFoundException("Không tìm thấy ảnh");
        galleryRepository.deleteById(id);
        return new ServiceResult(null, ServiceResult.SUCCESS, "Xoá ảnh thành công");
    }
}
