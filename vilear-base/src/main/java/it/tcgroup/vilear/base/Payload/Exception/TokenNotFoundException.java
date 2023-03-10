package it.tcgroup.vilear.base.Payload.Exception;

public class TokenNotFoundException extends NotFoundException {

    public TokenNotFoundException() {
        super("Token not found");
    }

}
