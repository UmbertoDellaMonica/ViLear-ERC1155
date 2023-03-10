package it.tcgroup.vilear.registration.Model;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegistrationModel {

    @NotNull
    private UserModel user;

    @NotNull
    private PersonModel person;

}
