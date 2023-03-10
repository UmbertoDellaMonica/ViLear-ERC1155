package it.tcgroup.vilear.course.Entity;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "logistics_courses")
@Getter
@Setter
public class LogisticCourseEntity {

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PrimaryKey implements Serializable {
        @Column(name = "logistic_id")
        private Integer logisticId;
        @Column(name = "course_id")
        private Integer courseId;

        public PrimaryKey(Integer logisticId, Integer courseId) {
            this.logisticId = logisticId;
            this.courseId = courseId;
        }
    }

    @EmbeddedId
    private PrimaryKey primaryKey;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @MapsId("courseId")
    private CourseEntity course;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    public Integer getLogisticId(){
        if(this.primaryKey == null) {
            return null;
        }else{
            return this.primaryKey.getLogisticId();
        }
    }

}
