package it.tcgroup.vilear.registration.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "partnership_candidations")
@Getter
@Setter
public class PartnerCandidationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = true)
    private UserEntity user;

    @Column(name = "request_date",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    private Timestamp requestDate;

    @Column(name = "decision_date")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp decisionDate;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}
