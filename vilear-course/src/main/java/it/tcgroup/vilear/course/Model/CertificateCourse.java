package it.tcgroup.vilear.course.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateCourse {

    private String nameCourse;
    private String releaseDate;

    private String user;
    private String teacher;

    public CertificateCourse(String nameCourse, String releaseDate, String user, String teacher) {
        this.nameCourse = nameCourse;
        this.releaseDate = releaseDate;
        this.user = user;
        this.teacher = teacher;
    }
}
