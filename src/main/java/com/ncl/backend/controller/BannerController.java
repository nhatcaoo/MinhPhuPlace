package com.ncl.backend.controller;

import com.ncl.backend.entity.Banner;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.PostCreatedModel;
import com.ncl.backend.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/api/v1"})
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @ResponseBody
    @GetMapping("/all/get-all-banner")
    public ResponseEntity getAllBanner() {
        return new ResponseEntity(bannerService.getAllBanner(), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/admin/edit-banner")
    public ResponseEntity editBanner(@RequestBody Banner banner) throws NotFoundException {
        return new ResponseEntity(bannerService.editBanner(banner), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/admin/create-banner") //post get put delete
    public ResponseEntity createBanner(@RequestBody Banner banner) {
        return new ResponseEntity(bannerService.createBanner(banner), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/admin/delete-banner") //post get put delete
    public ResponseEntity deleteBanner(@RequestParam (value = "id") Long id) throws NotFoundException {
        return new ResponseEntity(bannerService.deleteBanner(id), HttpStatus.OK);
    }
}
