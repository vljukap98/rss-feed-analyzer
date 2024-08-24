package com.ljakovic.rssfeedanalyzer.exception;

public class RssUrlsSizeException extends RuntimeException {
    public RssUrlsSizeException() {
    }

    public RssUrlsSizeException(String message) {
        super(message);
    }

    public RssUrlsSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
