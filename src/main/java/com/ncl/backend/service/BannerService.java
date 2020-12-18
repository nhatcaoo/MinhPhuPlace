package com.ncl.backend.service;

import com.ncl.backend.entity.Banner;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;

public interface BannerService {
    ServiceResult getAllBanner();
    ServiceResult createBanner(Banner banner);
    ServiceResult editBanner(Banner banner) throws NotFoundException;
    ServiceResult deleteBanner(Long id) throws  NotFoundException;
}
