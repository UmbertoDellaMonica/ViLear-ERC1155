package it.tcgroup.vilear.base.Payload.Exception;

public class Unahautorized extends RuntimeException{
    public Unahautorized() {
        super(" USER is NOT AUHTORIZED ");
    }
}
