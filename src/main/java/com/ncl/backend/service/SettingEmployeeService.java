package com.ncl.backend.service;

import com.ncl.backend.entity.EmployeeInfo;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;

public interface SettingEmployeeService {
    ServiceResult changeMail(EmployeeInfo employeeInfo) throws NotFoundException;

    ServiceResult addEmployee(EmployeeInfo employeeInfo);

    ServiceResult editEmployee(EmployeeInfo employeeInfo) throws NotFoundException;

    ServiceResult deleteEmployee(EmployeeInfo employeeInfo) throws NotFoundException;

}
