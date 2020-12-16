package com.ncl.backend.controller;

import com.ncl.backend.entity.Post;
import com.ncl.backend.exception.NotFoundException;
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
    public ResponseEntity createPost(@RequestBody PostCreatedModel post) {
        return new ResponseEntity(postSerivce.createPost(post), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/admin/edit-post") //post get put delete
    public ResponseEntity editPost(@RequestBody PostCreatedModel post) throws NotFoundException {
        return new ResponseEntity(postSerivce.editPost(post), HttpStatus.OK);
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
}
