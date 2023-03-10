package it.tcgroup.vilear.base.Payload.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found exception");
    }

}
