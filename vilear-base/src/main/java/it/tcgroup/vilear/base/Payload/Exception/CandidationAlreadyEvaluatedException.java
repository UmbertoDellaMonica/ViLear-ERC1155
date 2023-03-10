package it.tcgroup.vilear.base.Payload.Exception;

public class CandidationAlreadyEvaluatedException extends RuntimeException {

    public CandidationAlreadyEvaluatedException() {
        super("Candidation was already evaluated!");
    }

}
