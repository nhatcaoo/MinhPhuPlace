package com.ncl.backend.controller;

import com.ncl.backend.entity.Banner;
import com.ncl.backend.entity.EmployeeInfo;
import com.ncl.backend.exception.ExistedException;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.service.SettingEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/api/v1"})
public class SettingEmployeeController {
    @Autowired
    private SettingEmployeeService settingEmployeeService;

    @ResponseBody
    @GetMapping("/all/get-all-employee")
    public ResponseEntity getAllEmpInfo() {
        return new ResponseEntity(settingEmployeeService.showAllEmployee(), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/admin/edit-employee")
    public ResponseEntity editEmployee(@RequestBody EmployeeInfo employeeInfo) throws NotFoundException {
        return new ResponseEntity(settingEmployeeService.editEmployee(employeeInfo), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/admin/add-employee") //post get put delete
    public ResponseEntity addEmployee(@RequestBody EmployeeInfo employeeInfo) throws ExistedException {
        return new ResponseEntity(settingEmployeeService.addEmployee(employeeInfo), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/admin/delete-employee") //post get put delete
    public ResponseEntity deleteEmployee(@RequestBody EmployeeInfo employeeInfo) throws NotFoundException {
        return new ResponseEntity(settingEmployeeService.deleteEmployee(employeeInfo.getId()), HttpStatus.OK);
    }
}
