package com.ncl.backend.service;

import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.RoomCreatedModel;
import com.ncl.backend.model.ServiceResult;

public interface RoomService {
    ServiceResult getAllRoom();
    ServiceResult getOneRoom(Long id) throws NotFoundException;
    ServiceResult createRoom(RoomCreatedModel roomCreatedModel);
    ServiceResult editRoom(RoomCreatedModel roomCreatedModel) throws NotFoundException;
    ServiceResult deleteRoom(Long id) throws NotFoundException;
}
