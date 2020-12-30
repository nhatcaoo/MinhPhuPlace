package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Contact;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.ContactRepository;
import com.ncl.backend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Override
    public ServiceResult editContact(Contact contact) throws NotFoundException {
        contact.setId(1L);
        if (!contactRepository.existsById(contact.getId())) {
            throw new NotFoundException(Constant.POST_NOT_FOUND);
        }
        contactRepository.save(contact);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);
    }

    @Override
    public ServiceResult getContact() throws NotFoundException {
        if (!contactRepository.existsById(1L)) {
            throw new NotFoundException("Không tìm thấy thông tin liên hệ nào");
        }
        return new ServiceResult(contactRepository.findById(1L).get(), ServiceResult.SUCCESS, "");
    }
}
