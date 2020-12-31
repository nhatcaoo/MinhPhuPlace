package com.ncl.backend.repository;

import com.ncl.backend.entity.Room;
import com.ncl.backend.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    List<RoomImage> findAllByRoomId(Long roomId);
    Integer deleteAllByRoomId(Long roomId);
    RoomImage findFirstByRoomId(Long roomId);
}
