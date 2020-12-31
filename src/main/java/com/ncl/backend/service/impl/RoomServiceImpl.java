package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Room;
import com.ncl.backend.entity.RoomImage;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.*;
import com.ncl.backend.repository.RoomImageRepository;
import com.ncl.backend.repository.RoomRepository;
import com.ncl.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomImageRepository roomImageRepository;

    @Override
    public ServiceResult getAllRoom() {
        List<Room> roomList = roomRepository.findAll();
        List<RoomDto> roomDtoList = new ArrayList<>();

        for(Room r:roomList){
            RoomDto roomDto = new RoomDto();
            RoomImage ri = roomImageRepository.findFirstByRoomId(r.getId());
            roomDto.setRoom(r);
            if(ri!=null)
            {
                roomDto.setImage(ri.getImg());
            }
            roomDtoList.add(roomDto);
        }
        return new ServiceResult(roomDtoList, ServiceResult.SUCCESS, Constant.EMPTY);
    }

    @Override
    public ServiceResult getOneRoom(Long id) throws NotFoundException {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException(Constant.ROOM_NOT_FOUND);
        }
        RoomCreatedModel finalRoom = new RoomCreatedModel();
        finalRoom.setRoom(roomRepository.findById(id).get());
        finalRoom.setList(roomImageRepository.findAllByRoomId(id).stream().map(p -> p.getImg()).collect(Collectors.toList()));
        return new ServiceResult(finalRoom, ServiceResult.SUCCESS, Constant.EMPTY);
    }
    @org.springframework.transaction.annotation.Transactional(rollbackFor = Throwable.class)
    @Override
    public ServiceResult createRoom(RoomCreatedModel roomCreatedModel) {
        Room r = roomRepository.saveAndFlush(roomCreatedModel.getRoom());
        List<String> listImage = roomCreatedModel.getList();
        for(String s : listImage){
            RoomImage roomImage = new RoomImage();
            roomImage.setImg(s);
            roomImage.setRoom(r);
            roomImageRepository.save(roomImage);
        }
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.ROOM_CREATE_SUCCESS);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor = Throwable.class)
    public ServiceResult editRoom(RoomCreatedModel roomCreatedModel) throws NotFoundException {
        Long roomId = roomCreatedModel.getRoom().getId();
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException(Constant.ROOM_NOT_FOUND);
        }
        roomRepository.save(roomCreatedModel.getRoom());
        roomImageRepository.deleteAllByRoomId(roomId);
        List<String> listImage = roomCreatedModel.getList();
        Room r = new Room();
        r.setId(roomId);
        for (String i : listImage) {
            RoomImage roomImage = new RoomImage();
            roomImage.setImg(i);
            roomImage.setRoom(r);
            roomImageRepository.save(roomImage);
        }
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);
    }

    @Override
    @Transactional
    public ServiceResult deleteRoom(Long id) throws NotFoundException {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException(Constant.ROOM_NOT_FOUND);
        }
        roomImageRepository.deleteAllByRoomId(id);
        roomRepository.deleteById(id);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.DELETE_SUCCESS);
    }

}
