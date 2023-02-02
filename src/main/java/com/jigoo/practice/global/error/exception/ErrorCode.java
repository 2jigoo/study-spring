package com.jigoo.practice.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value")
    , INVALID_TYPE_VALUE(400, "C002", "Invalid Type Value")
    , ENTITY_NOT_FOUND(400, "C003", "Entity Not Found")
    , HANDLE_ACCESS_DENIED(403, "C004", "Access Denied")
    , METHOD_NOT_ALLOWED(405, "C005", "Method Not Allowed")
    , INTERNAL_SERVER_ERROR(500, "C006", "Internal Server Error")


    ;

    private String code;
    private String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
