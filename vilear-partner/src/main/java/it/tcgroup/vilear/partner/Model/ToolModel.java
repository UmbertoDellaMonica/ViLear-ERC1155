package it.tcgroup.vilear.partner.Model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Getter
@Setter
public class ToolModel {
    private Integer id;

    private PartnerModel partner;

    private String name;

    private String description;

    private Float dailyCost;

    private Integer availability;

    @CreationTimestamp
    private Timestamp createdAt;
}
