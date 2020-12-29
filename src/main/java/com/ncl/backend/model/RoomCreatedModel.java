package com.ncl.backend.model;

import com.ncl.backend.entity.Room;
import com.ncl.backend.entity.RoomImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreatedModel {
    private Room room;
    private List<String> list;
}
