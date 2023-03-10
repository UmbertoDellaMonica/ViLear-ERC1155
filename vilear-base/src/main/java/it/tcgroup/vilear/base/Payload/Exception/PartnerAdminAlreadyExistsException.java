package it.tcgroup.vilear.base.Payload.Exception;

public class PartnerAdminAlreadyExistsException extends AlreadyExistsException {
    public PartnerAdminAlreadyExistsException() {
        super("ALREADY PARTNER ADMIN IS SETTING A TRUE");
    }
}
