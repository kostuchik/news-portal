package com.news.portal.exception.custom_exception;

public class CommentException extends RuntimeException {

    public CommentException() {
        super();
    }

    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
