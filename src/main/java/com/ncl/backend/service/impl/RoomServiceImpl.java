package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Post;
import com.ncl.backend.entity.PostImage;
import com.ncl.backend.entity.Room;
import com.ncl.backend.entity.RoomImage;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.*;
import com.ncl.backend.repository.PostImageRepository;
import com.ncl.backend.repository.RoomImageRepository;
import com.ncl.backend.repository.RoomRepository;
import com.ncl.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ServiceResult getOneRoom(Long id) throws NotFoundException {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException(Constant.ROOM_NOT_FOUND);
        }
        RoomCreatedModel finalRoom = new RoomCreatedModel();
        finalRoom.setRoom(roomRepository.findById(id).get());
        finalRoom.setList(roomImageRepository.findAllByRoomId(id));
        return new ServiceResult(finalRoom, ServiceResult.SUCCESS, Constant.EMPTY);
    }

    @Override
    public ServiceResult createRoom(RoomCreatedModel roomCreatedModel) {
        Room r = roomRepository.saveAndFlush(roomCreatedModel.getRoom());
        List<RoomImage> listImage = roomCreatedModel.getList();
        listImage.get(0).setType(Constant.COVER_IMAGE);
        for (RoomImage i : listImage) {
            r.setId(r.getId());
            i.setRoom(r);
            roomImageRepository.save(i);
        }
        return new ServiceResult(roomRepository.findAll(), ServiceResult.SUCCESS, Constant.ROOM_CREATE_SUCCESS);
    }

    @Override
    public ServiceResult editRoom(RoomCreatedModel roomCreatedModel) throws NotFoundException {
        Long roomId = roomCreatedModel.getRoom().getId();
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException(Constant.ROOM_NOT_FOUND);
        }
        roomImageRepository.deleteAllByRoomId(roomId);
        roomRepository.save(roomCreatedModel.getRoom());
        List<RoomImage> listImage = roomCreatedModel.getList();
        for (RoomImage i : listImage) {
            Room r = new Room();
            r.setId(roomId);
            i.setRoom(r);
            roomImageRepository.save(i);
        }
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);
    }

    @Override
    public ServiceResult deleteRoom(Long id) throws NotFoundException {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException(Constant.ROOM_NOT_FOUND);
        }
        roomRepository.deleteById(id);
        roomImageRepository.deleteAllByRoomId(id);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.DELETE_SUCCESS);
    }

    @Autowired
    private RoomImageRepository roomImageRepository;
}