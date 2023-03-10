package it.tcgroup.vilear.base.Payload.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ToolResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("partner")
    private PartnerResponse partner;

    @JsonProperty( "name_tool")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("daily_cost")
    private Float dailyCost;

    @JsonProperty("availability")
    private Integer availability;

    @JsonProperty("created_at")
    private Timestamp createdAt;
}
