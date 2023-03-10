package it.tcgroup.vilear.partner.Model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class TeacherModel {

    private Integer id;

    private PartnerModel partner;

    private Timestamp createdAt;



    public void setPartnerId(Integer studentPartecipationId) {
        if( partner == null ) {
            partner = new PartnerModel();
        }
        this.partner.setId(studentPartecipationId);
    }
}
