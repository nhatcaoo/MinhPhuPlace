package com.ncl.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private String fullName;

    private String phoneNumber;

    private String serviceName;

    private String date;
}
