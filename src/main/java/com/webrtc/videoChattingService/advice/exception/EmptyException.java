package com.webrtc.videoChattingService.advice.exception;

public class EmptyException extends RuntimeException {

    public EmptyException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmptyException(String msg) {
        super(msg);
    }

    public EmptyException() {
        super();
    }
}
