package com.stream.hub.userMgmt.dto.genric;


import lombok.Data;

@Data
public class ApiValidationError {

    private String field;

    private String rejectedValue;

    private String errorMessage;

}