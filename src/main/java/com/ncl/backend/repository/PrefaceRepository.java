package com.ncl.backend.repository;

import com.ncl.backend.entity.Preface;
import com.ncl.backend.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefaceRepository extends JpaRepository<Preface, Long> {

}
