package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PartnerCandidationRequest implements Serializable {

    // Partner Dati
    @JsonProperty("partner")
    @NotNull
    private PartnerRequest partner;

    // User Dati
    @JsonProperty("user")
    @NotNull
    private UserRequest user;

    // Note
    @JsonProperty("note")
    private String note;

}
