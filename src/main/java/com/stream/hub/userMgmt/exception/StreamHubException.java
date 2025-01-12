package com.stream.hub.userMgmt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamHubException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 4642679525595927897L;

    private String message;

    private int statusCode;

}
