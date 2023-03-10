package it.tcgroup.vilear.base.Payload.Exception;

public class RolePersonAlreadyExistsException extends AlreadyExistsException{
    public RolePersonAlreadyExistsException() {
        super("ROLE Person Exists");
    }
}
