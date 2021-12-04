package com.example.myproject.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE,
        reason = "File is not image/jpeg or file size >= 5Bb")
public class FileNotSupportedOperation extends RuntimeException {
    private String fileName;
    private String fileSize;

    public FileNotSupportedOperation(String fileName, String fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

}
