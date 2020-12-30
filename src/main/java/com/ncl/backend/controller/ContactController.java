package com.ncl.backend.controller;

import com.ncl.backend.entity.Contact;
import com.ncl.backend.entity.Preface;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/api/v1"})
public class ContactController {
    @Autowired
    ContactService contactService;
    @ResponseBody
    @PutMapping("/admin/edit-contact") //post get put delete
    public ResponseEntity editContact(@RequestBody Contact contact) throws NotFoundException {
        return new ResponseEntity(contactService.editContact(contact), HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/all/get-contact") //post get put delete
    public ResponseEntity getContact() throws NotFoundException {
        return new ResponseEntity(contactService.getContact(), HttpStatus.OK);
    }
}
