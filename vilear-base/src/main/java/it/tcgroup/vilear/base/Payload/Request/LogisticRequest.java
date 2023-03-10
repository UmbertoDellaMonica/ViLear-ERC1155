package it.tcgroup.vilear.base.Payload.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class LogisticRequest implements Serializable {
    @JsonProperty("address")
    private String address;
    @JsonProperty("city")
    private String city;
    @JsonProperty("persons_capacity")
    private Integer personCapacity;
    @JsonProperty("daily_cost")
    private Float dailyCost;

}
