package com.ncl.backend.controller;

import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.RoomCreatedModel;
import com.ncl.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/api/v1"})
public class RoomController {
    @Autowired
    private RoomService roomService;

    @ResponseBody
    @GetMapping("/all/get-all-room") //post get put delete
    public ResponseEntity getAllRoom() {
        return new ResponseEntity(roomService.getAllRoom(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/all/get-one-room/{id}") //post get put delete
    public ResponseEntity getOnePost(@PathVariable(name = "id") Long id) throws NotFoundException {
        return new ResponseEntity(roomService.getOneRoom(id), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/admin/create-room") //post get put delete
    public ResponseEntity createRoom(@RequestBody RoomCreatedModel roomCreatedModel) {
        return new ResponseEntity(roomService.createRoom(roomCreatedModel), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/admin/edit-room") //post get put delete
    public ResponseEntity editPost(@RequestBody RoomCreatedModel roomCreatedModel) throws NotFoundException {
        return new ResponseEntity(roomService.editRoom(roomCreatedModel), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/admin/delete-room") //post get put delete
    public ResponseEntity deleteRoom(@RequestParam(value = "id") Long id) throws NotFoundException {
        return new ResponseEntity(roomService.deleteRoom(id), HttpStatus.OK);
    }
}
