package com.stream.hub.userMgmt.exception;

import com.stream.hub.userMgmt.constants.GenericConstant;
import com.stream.hub.userMgmt.dto.genric.ApiResponse;
import com.stream.hub.userMgmt.dto.genric.ApiValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class StreamHubExceptionAdvice {


    @ExceptionHandler(StreamHubException.class)
    public ResponseEntity<ApiResponse<Object>> handleStreamHubException(StreamHubException streamHubException) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(streamHubException.getMessage(), streamHubException.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        BindingResult br = ex.getBindingResult();
        List<ApiValidationError> apiValidationErrors = new ArrayList<>();
        for (FieldError oe : br.getFieldErrors()) {
            ApiValidationError api = new ApiValidationError();
            api.setErrorMessage(Objects.requireNonNull(oe.getDefaultMessage()));
            api.setField(oe.getField());
            api.setRejectedValue(oe.getRejectedValue() + "");
            apiValidationErrors.add(api);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                 HttpStatus.BAD_REQUEST.value(),GenericConstant.FAILURE, apiValidationErrors));

    }

}
