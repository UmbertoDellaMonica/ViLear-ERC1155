package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.tcgroup.vilear.base.Payload.Response.LogisticResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherResponse;
import it.tcgroup.vilear.base.Payload.Response.ToolResponse;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PartnerRequest implements Serializable {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("vat")
    private String vat;
    @JsonProperty("business_name")
    private String businessName;
    @JsonProperty("address_billing")
    private String addressBilling;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("is_admin")
    private Boolean admin;
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("token")
    private UUID token;
    @JsonProperty("teachers")
    private List<TeacherResponse> teacherList;
    @JsonProperty("logistics")
    private List<LogisticResponse> logisticList;
    @JsonProperty("tools")
    private List<ToolResponse> toolsList;

}
