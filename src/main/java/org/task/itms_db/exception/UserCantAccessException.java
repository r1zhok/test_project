package org.task.itms_db.exception;

public class UserCantAccessException extends Exception {
    public UserCantAccessException(String message) {
        super(message);
    }
}
