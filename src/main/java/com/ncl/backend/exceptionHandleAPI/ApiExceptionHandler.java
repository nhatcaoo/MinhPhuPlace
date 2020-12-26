package com.ncl.backend.exceptionHandleAPI;

import com.ncl.backend.exception.ExistedException;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.exception.NullObjectException;
import com.ncl.backend.model.ServiceResult;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;

@RestControllerAdvice
@EnableWebMvc
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ServiceResult> handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        ex.printStackTrace();
        return new ResponseEntity(new ServiceResult(null, ServiceResult.FAIL, "Đã xảy ra lỗi không mong muốn"), HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity(new ServiceResult(null, ServiceResult.FAIL, "Bad request"), HttpStatus.BAD_REQUEST);
    }

    /**
     * NotFoundException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ServiceResult> NotFoundException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity(new ServiceResult(null, ServiceResult.FAIL, ex.getMessage()), HttpStatus.OK);

    }

    @ExceptionHandler(NullObjectException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ServiceResult> NullObjectException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity(new ServiceResult(null, ServiceResult.FAIL, ex.getMessage()), HttpStatus.OK);

    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServiceResult> handleBadRequest(Exception ex, WebRequest request) {
        ex.printStackTrace();

        return new ResponseEntity(new ServiceResult(null, ServiceResult.FAIL, ex.getMessage()), HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(ExistedException.class)
    public ResponseEntity<ServiceResult> handleExistedRequest(Exception ex, WebRequest request) {
        ex.printStackTrace();

        return new ResponseEntity(new ServiceResult(null, ServiceResult.FAIL, ex.getMessage()), HttpStatus.OK);

    }

}
