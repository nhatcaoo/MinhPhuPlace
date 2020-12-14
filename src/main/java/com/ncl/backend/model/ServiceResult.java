package com.ncl.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult {
    public Object data;
    public String status;
    public String message;

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
}
