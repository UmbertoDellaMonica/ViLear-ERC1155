package it.tcgroup.vilear.base.Payload.Exception;

public class InvitationExpiredExcpetion extends DateExpiredException {

    public InvitationExpiredExcpetion() {
        super("Invitation date was expired");
    }

}
