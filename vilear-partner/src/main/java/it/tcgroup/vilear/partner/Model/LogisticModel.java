package it.tcgroup.vilear.partner.Model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Getter
@Setter
public class LogisticModel {

    private Integer id;

    private String address;

    private String city;

    private Integer personCapacity;

    private Float dailyCost;

    @CreationTimestamp
    private Timestamp createdAt;

    private PartnerModel partner;

}
