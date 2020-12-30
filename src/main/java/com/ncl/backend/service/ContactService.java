package com.ncl.backend.service;

import com.ncl.backend.entity.Contact;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;

public interface ContactService {
    ServiceResult editContact(Contact contact) throws NotFoundException;

    ServiceResult getContact() throws NotFoundException;
}
