package com.example.demo.domain.exception;

public class ParseGPSFileException extends Exception {
    private String fileName;

    public ParseGPSFileException(String errorMessage) {
        super(errorMessage);
    }

    public ParseGPSFileException(String fileName, Throwable ex) {
        super(ex);
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
