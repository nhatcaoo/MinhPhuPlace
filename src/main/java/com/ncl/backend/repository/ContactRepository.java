package com.ncl.backend.repository;

import com.ncl.backend.entity.Banner;
import com.ncl.backend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
