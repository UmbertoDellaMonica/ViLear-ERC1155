package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PartnerResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;

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
}
