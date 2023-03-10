package it.tcgroup.vilear.base.Payload.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class JwtResponse implements Serializable {
    private String token;
}
