package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.EmployeeInfo;
import com.ncl.backend.exception.ExistedException;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.EmployeeInfoRepository;
import com.ncl.backend.service.SettingEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.List;

@Service
public class SettingEmployeeImpl implements SettingEmployeeService {
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;

    @Override
    public ServiceResult changeMail(EmployeeInfo employeeInfo) throws NotFoundException {
        if (employeeInfoRepository.existsById(employeeInfo.getId())) {
            throw new NotFoundException(Constant.EMPLOYEE_NOT_FOUND);
        }
        employeeInfoRepository.save(employeeInfo);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.CHANGE_MAIL_SUCCESS);
    }

    @Override
    public ServiceResult addEmployee(EmployeeInfo employeeInfo) throws ExistedException {
        if(employeeInfoRepository.existsByEmail(employeeInfo.getEmail())){
            throw new ExistedException(Constant.EMPLOYEE_EXISTED);
        }
        employeeInfoRepository.save(employeeInfo);
        return new ServiceResult(employeeInfoRepository.findAll(), ServiceResult.SUCCESS, Constant.ADD_EMPLOYEE_SUCCESS);
    }

    @Override
    public ServiceResult editEmployee(EmployeeInfo employeeInfo) throws NotFoundException {
        if (employeeInfoRepository.existsById(employeeInfo.getId())) {
            throw new NotFoundException(Constant.EMPLOYEE_NOT_FOUND);
        }
        employeeInfoRepository.deleteById(employeeInfo.getId());
        employeeInfoRepository.save(employeeInfo);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);
    }

    @Override
    public ServiceResult deleteEmployee(Long id) throws NotFoundException {
        if (employeeInfoRepository.existsById(id)) {
            throw new NotFoundException(Constant.EMPLOYEE_NOT_FOUND);
        }
        employeeInfoRepository.deleteById(id);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.DELETE_SUCCESS);
    }

    @Override
    public ServiceResult showAllEmployee() {
        List<EmployeeInfo> emplList = employeeInfoRepository.findAll();
        return new ServiceResult(employeeInfoRepository.findAll(), ServiceResult.SUCCESS, Constant.EMPTY);
    }
}
