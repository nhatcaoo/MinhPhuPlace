package com.ncl.backend.controller;

import com.ncl.backend.entity.Post;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.service.PostSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("post")
public class PostController {
    @Autowired
    private PostSerivce postSerivce;

    @ResponseBody
    @GetMapping("/get-all-service") //post get put delete
    public ResponseEntity getAllService() {
        return new ResponseEntity(postSerivce.getAllServicePost(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/get-one-post") //post get put delete
    public ResponseEntity getOnePost(@PathVariable(name = "id") Long id) throws NotFoundException {
        return new ResponseEntity(postSerivce.getOnePost(id), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/create-post") //post get put delete
    public ResponseEntity createPost(@RequestBody Post post) {
        return new ResponseEntity(postSerivce.createPost(post), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/edit-post") //post get put delete
    public ResponseEntity editPost(@RequestBody Post post) throws NotFoundException {
        return new ResponseEntity(postSerivce.editPost(post), HttpStatus.OK);
    }


}
