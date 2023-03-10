package it.tcgroup.vilear.base.Payload.Exception;

public class PartnerNotFoundException extends NotFoundException{
    public PartnerNotFoundException() {
        super("Partner not found");
    }
}
