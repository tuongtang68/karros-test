package com.example.demo.domain.exception.handler;

import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.exception.ParseGPSFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
        NotFoundException.class,
        ParseGPSFileException.class,
        Exception.class
    })
    public ResponseEntity<APIError> handleGeneralException(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        int errorCode = 0;

        if (ex instanceof NotFoundException) {
            errorCode = HttpStatus.NOT_FOUND.value();
        }
        else if (ex instanceof ParseGPSFileException) {
            errorCode = HttpStatus.UNPROCESSABLE_ENTITY.value();
        }
        else {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        return ResponseEntity.status(errorCode)
                             .body(new APIError(errorMessage, errorCode));
    }

    public static class APIError {
        String errorMessage;
        int errorCode;

        public APIError(String errorMessage, int errorCode) {
            this.errorMessage = errorMessage;
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public int getErrorCode() {
            return this.errorCode;
        }
    }
}
