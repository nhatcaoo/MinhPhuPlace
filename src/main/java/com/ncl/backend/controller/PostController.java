package com.ncl.backend.controller;

import com.ncl.backend.entity.Preface;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.exception.NullObjectException;
import com.ncl.backend.model.PostCreatedModel;
import com.ncl.backend.service.PostSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/api/v1"})
public class PostController {
    @Autowired
    private PostSerivce postSerivce;

    @ResponseBody
    @GetMapping("/all/get-all-service") //post get put delete
    public ResponseEntity getAllService() {
        return new ResponseEntity(postSerivce.getAllServicePost(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/all/get-one-post/{id}") //post get put delete
    public ResponseEntity getOnePost(@PathVariable(name = "id") Long id) throws NotFoundException {
        return new ResponseEntity(postSerivce.getOnePost(id), HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping("/admin/create-post") //post get put delete
    public ResponseEntity createPost(@RequestBody PostCreatedModel post) throws NullObjectException {
        return new ResponseEntity(postSerivce.createPost(post), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/admin/edit-post") //post get put delete
    public ResponseEntity editPost(@RequestBody PostCreatedModel post) throws NotFoundException {
        return new ResponseEntity(postSerivce.editPost(post), HttpStatus.OK);
    }
    @ResponseBody
    @PutMapping("/admin/edit-preface") //post get put delete
    public ResponseEntity editPreface(@RequestBody Preface post) throws NotFoundException {
        return new ResponseEntity(postSerivce.editPreface(post), HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/all/get-preface") //post get put delete
    public ResponseEntity getPreface() throws NotFoundException {
        return new ResponseEntity(postSerivce.getPreface(), HttpStatus.OK);
    }
    @ResponseBody
    @DeleteMapping("/admin/delete-post") //post get put delete
    public ResponseEntity deletePost(@RequestParam (value = "id") Long id) throws NotFoundException {
        return new ResponseEntity(postSerivce.deletePost(id), HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/all/get-all-event") //post get put delete
    public ResponseEntity getAllEvent() {
        return new ResponseEntity(postSerivce.getAllEvent(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/all/get-all-homepage-service") //post get put delete
    public ResponseEntity getAllHomepageService() {
        return new ResponseEntity(postSerivce.getHomepageServicePost(), HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/all/get-all-homepage-service-v2") //post get put delete
    public ResponseEntity getHomepageService() {
        return new ResponseEntity(postSerivce.getHomepageServicePostOnePic(), HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/all/get-minor-service") //post get put delete
    public ResponseEntity getAllMinorService() {
        return new ResponseEntity(postSerivce.getAllMinorService(), HttpStatus.OK);
    }
}
