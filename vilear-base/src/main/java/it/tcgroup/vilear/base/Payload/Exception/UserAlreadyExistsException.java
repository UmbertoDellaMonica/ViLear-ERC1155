package it.tcgroup.vilear.base.Payload.Exception;

public class UserAlreadyExistsException extends AlreadyExistsException {

    public UserAlreadyExistsException() {
        super("User already exists");
    }

}
