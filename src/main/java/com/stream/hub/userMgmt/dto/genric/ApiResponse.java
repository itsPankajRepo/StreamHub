package com.stream.hub.userMgmt.dto.genric;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private int statusCode;
    private String status;
    private T result;


    public ApiResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ApiResponse(String message, int statusCode, String status, T result) {
        this.message = message;
        this.statusCode = statusCode;
        this.status = status;
        this.result = result;
    }
}
