package it.tcgroup.vilear.base.Payload.Exception;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException() {
        super("COURSE NOT FOUND");
    }
}
