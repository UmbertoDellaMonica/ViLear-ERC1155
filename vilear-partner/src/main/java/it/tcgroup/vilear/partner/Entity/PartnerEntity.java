package it.tcgroup.vilear.partner.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "partners")
@Getter
@Setter
public class PartnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id",unique = true)
    private Integer userId;

    @OneToMany(mappedBy = "partner",cascade = CascadeType.REMOVE)
    private List<TeacherEntity> teacherList;

    @OneToMany(mappedBy = "partner",cascade = CascadeType.REMOVE)
    private List<LogisticEntity> logisticList;

    @OneToMany(mappedBy = "partner",cascade = CascadeType.REMOVE)
    private List<ToolEntity> toolsList;

    @Column(name = "vat",nullable = false)
    private String vat;

    @Column(name = "business_name",nullable = false)
    private String businessName;

    @Column(name = "address_billing",nullable = false)
    private String addressBilling;

    @Column(name = "fax",nullable = false)
    private String fax;

    @Column(name = "token_partner")
    private UUID token=UUID.randomUUID();

    @Column(name = "is_admin",nullable = false)
    private Boolean admin;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}
