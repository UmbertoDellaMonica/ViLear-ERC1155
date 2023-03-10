package it.tcgroup.vilear.base.Payload.Exception;

public class CourseBadStatusException extends RuntimeException {
    public CourseBadStatusException() {
        super("BAD STAUS COURSE, IS NOT ACTIVE");
    }
}
