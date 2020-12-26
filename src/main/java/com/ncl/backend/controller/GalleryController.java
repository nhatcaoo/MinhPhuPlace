package com.ncl.backend.controller;

import com.ncl.backend.entity.Gallery;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/api/v1"})
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    @ResponseBody
    @GetMapping("/all/gallery/get-all-pic") //post get put delete
    public ResponseEntity getAllPic() {
        return new ResponseEntity(galleryService.getAllPic(), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/all/gallery/add-to-gallery") //post get put delete
    public ResponseEntity createPic(@RequestBody Gallery pic) {
        return new ResponseEntity(galleryService.addPic(pic), HttpStatus.OK);
    }
    @ResponseBody
    @DeleteMapping("/all/gallery/delete-pic/{id}") //post get put delete
    public ResponseEntity deletePic(@RequestParam(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity(galleryService.deletePic(id), HttpStatus.OK);
    }
}
