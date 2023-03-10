package it.tcgroup.vilear.base.Payload.Exception;

public class StudentEvaluationAlreadyExistsException extends AlreadyExistsException {
    public StudentEvaluationAlreadyExistsException() {
        super("Evaluation student already exists!");
    }
}
