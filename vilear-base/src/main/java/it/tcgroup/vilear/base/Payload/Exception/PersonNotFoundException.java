package it.tcgroup.vilear.base.Payload.Exception;

public class PersonNotFoundException extends NotFoundException{
    public PersonNotFoundException() {
        super("PERSON NOT FOUND");
    }
}
