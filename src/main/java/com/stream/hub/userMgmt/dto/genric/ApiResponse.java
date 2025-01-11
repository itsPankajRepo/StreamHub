package com.stream.hub.userMgmt.dto.genric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
