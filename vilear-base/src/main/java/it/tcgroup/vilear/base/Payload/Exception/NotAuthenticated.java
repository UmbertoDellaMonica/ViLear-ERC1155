package it.tcgroup.vilear.base.Payload.Exception;

public class NotAuthenticated extends RuntimeException{
    public NotAuthenticated() {
        super("USER IS NOT AUTHENTICATED");
    }
}
