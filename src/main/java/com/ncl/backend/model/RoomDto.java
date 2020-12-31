package com.ncl.backend.model;

import com.ncl.backend.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private Room room;
    private String image;
}
