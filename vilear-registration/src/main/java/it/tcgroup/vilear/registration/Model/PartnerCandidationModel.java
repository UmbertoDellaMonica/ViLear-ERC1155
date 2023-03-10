package it.tcgroup.vilear.registration.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Getter
@Setter
public class PartnerCandidationModel {

    private Integer id;

    private PartnerModel partner;

    private UserModel user;

    private String note;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp requestDate;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp decisionDate;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreationTimestamp
    private Timestamp createdAt;
}
