package it.tcgroup.vilear.base.Payload.Exception;

public class UserInvitationNotFoundException extends NotFoundException {
    public UserInvitationNotFoundException() {
        super("UserInvitation NOT FOUND");
    }
}
