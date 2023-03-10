package it.tcgroup.vilear.course.Entity;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "students_partecipation")
@Getter
@Setter
public class StudentPartecipationEntity {

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PrimaryKey implements Serializable {
        @Column(name = "person_id",nullable = false)
        private Integer personId;
        @Column(name = "course_id")
        private Integer courseId;

        public PrimaryKey(Integer personId, Integer courseId) {
            this.personId = personId;
            this.courseId = courseId;
        }
    }

    @EmbeddedId
    private PrimaryKey primaryKey;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @MapsId("courseId")
    private CourseEntity course;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    /*@OneToMany(mappedBy = "studentPartecipation",fetch = FetchType.LAZY)
    private List<StudentEvaluationEntity> studentEvaluationList;*/

    public Integer getPersonId(){
        if(this.primaryKey == null) {
            return null;
        }else{
            return this.primaryKey.getPersonId();
        }
    }

}
