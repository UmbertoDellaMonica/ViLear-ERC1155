package it.tcgroup.vilear.base.Payload.Exception;

public class CourseAlreadyExistsException extends AlreadyExistsException {
    public CourseAlreadyExistsException() {
        super("Already exists Course");
    }
}
