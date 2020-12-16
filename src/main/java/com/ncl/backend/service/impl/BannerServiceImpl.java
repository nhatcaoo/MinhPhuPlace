package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Banner;
import com.ncl.backend.entity.Post;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.BannerRepository;
import com.ncl.backend.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.OutputKeys;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerRepository bannerRepository;


    @Override
    public ServiceResult getAllBanner() {
        List<Banner> bannersList = bannerRepository.findAll();
        return new ServiceResult(bannersList, ServiceResult.SUCCESS,Constant.EMPTY);
    }

    @Override
    public ServiceResult createBanner(Banner banner) {
        bannerRepository.save(banner);
        return new ServiceResult(bannerRepository.findAll(), ServiceResult.SUCCESS, Constant.ADD_SUCCESS);
    }

    @Override
    public ServiceResult editBanner(Banner banner) throws NotFoundException {
        if(!bannerRepository.existsById(banner.getId())){
            throw new NotFoundException(Constant.BANNER_NOT_FOUND);
        }
        bannerRepository.save(banner);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);
    }

    @Override
    public ServiceResult deleteBanner(Long id) throws NotFoundException{
        if(!bannerRepository.existsById(id)){
            throw new NotFoundException(Constant.BANNER_NOT_FOUND);
        }
        bannerRepository.deleteById(id);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.DELETE_SUCCESS);
    }
}
