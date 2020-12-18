package com.ncl.backend.repository;

import com.ncl.backend.entity.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {
    boolean existsByEmail(String email);
}
