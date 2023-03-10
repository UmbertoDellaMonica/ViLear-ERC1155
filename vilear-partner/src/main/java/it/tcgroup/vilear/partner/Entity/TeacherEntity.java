package it.tcgroup.vilear.partner.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "teachers",uniqueConstraints ={
        @UniqueConstraint(columnNames = {"id", "partner_id"})
})
@Getter
@Setter
public class TeacherEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}
