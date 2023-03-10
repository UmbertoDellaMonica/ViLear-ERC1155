package it.tcgroup.vilear.registration.Service;


import it.tcgroup.vilear.registration.Model.PersonModel;

public interface PersonService {

    PersonModel add(PersonModel personModel);

    /**
     * Recupera i dati di  una Persona tramite :
     * @param personId id della persona
     * @return una Persona
     */
    PersonModel get(Integer personId);

    /**
     * Aggiorna i dati angrafici di una persona
     * @param personModel contiene i nuovi dati anagrafici
     * @param personId id della persona
     * @return una Persona con dati anagrafici aggiornati
     */
    PersonModel update(PersonModel personModel,Integer personId);

    /**
     * Elimina i dati anagrafici di una Persona
     * @param personId id della Persona
     */
    void delete(Integer personId);
}
