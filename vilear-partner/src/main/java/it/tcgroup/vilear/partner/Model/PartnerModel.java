package it.tcgroup.vilear.partner.Model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PartnerModel {

    private Integer id;

    private Integer userId;

    private List<TeacherModel> teacherList;

    private List<LogisticModel> logisticList;

    private List<ToolModel> toolsList;

    private String vat;

    private String businessName;

    private String addressBilling;

    private String fax;

    private Boolean admin;

    private String phoneNumber;

    private UUID token;

    @CreationTimestamp
    private Timestamp createdAt;


}
