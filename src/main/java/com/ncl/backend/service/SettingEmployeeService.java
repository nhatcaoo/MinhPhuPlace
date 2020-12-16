package com.ncl.backend.service;

import com.ncl.backend.entity.EmployeeInfo;
import com.ncl.backend.exception.ExistedException;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;

import java.security.Provider;

public interface SettingEmployeeService {
    ServiceResult changeMail(EmployeeInfo employeeInfo) throws NotFoundException;

    ServiceResult addEmployee(EmployeeInfo employeeInfo) throws ExistedException;

    ServiceResult editEmployee(EmployeeInfo employeeInfo) throws NotFoundException;

    ServiceResult deleteEmployee(Long id) throws NotFoundException;

    ServiceResult showAllEmployee();
}
