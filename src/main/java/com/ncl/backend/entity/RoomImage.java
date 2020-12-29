package com.ncl.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "room_image")
public class RoomImage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "brief", nullable = true)
    private String brief;

    @Column(name = "img", nullable = false, length = 1000000)
    private String img;

    @Column(name = "type", nullable = true)
    private String type;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private Room room;
}
