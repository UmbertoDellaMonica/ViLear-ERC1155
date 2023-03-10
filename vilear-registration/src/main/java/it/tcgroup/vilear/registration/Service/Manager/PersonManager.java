package it.tcgroup.vilear.registration.Service.Manager;


import it.tcgroup.vilear.registration.Entity.PersonEntity;
import it.tcgroup.vilear.registration.Model.PersonModel;

public interface PersonManager {

    PersonEntity save(PersonModel personModel);

    PersonEntity get(Integer personId);

    void delete(Integer personId);
}
