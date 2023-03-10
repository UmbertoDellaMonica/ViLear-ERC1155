package it.tcgroup.vilear.base.Payload.Exception;

public class TeacherAlreadyExistsException extends AlreadyExistsException {
    public TeacherAlreadyExistsException() {
        super("TEACHER EXISTS");
    }
}
