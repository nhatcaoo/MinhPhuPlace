package com.ncl.backend.controller.exceptionHandleAPI;

import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ServiceResult> handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        ex.printStackTrace();
        return new ResponseEntity(new ServiceResult(null, ServiceResult.FAIL, "Đã xảy ra lỗi không mong muốn"), HttpStatus.OK);

    }

    /**
     * NotFoundException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServiceResult> NotFoundException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity(new ServiceResult(null, ServiceResult.FAIL, ex.getMessage()), HttpStatus.OK);

    }
}
