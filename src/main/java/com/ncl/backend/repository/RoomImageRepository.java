package com.ncl.backend.repository;

import com.ncl.backend.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    RoomImage findByRoomIdAndType(Long roomId, String type);

    List<RoomImage> findAllByRoomId(Long roomId);

    Integer deleteAllByRoomId(Long roomId);
}
