package it.tcgroup.vilear.base.Payload.Exception;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException() {
        super("User not active Exception");
    }
}
