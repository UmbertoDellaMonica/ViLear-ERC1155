package it.tcgroup.vilear.base.Payload.Exception;

public class RoleNotFoundException extends NotFoundException{
    public RoleNotFoundException() {
        super("ROLE NOT FOUND");
    }
}
