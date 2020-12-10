package com.ncl.backend.model;

public class ServiceResult {
    public Object data;
    public String message;
    public String status;
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public ServiceResult(Object data, String status, String message) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
