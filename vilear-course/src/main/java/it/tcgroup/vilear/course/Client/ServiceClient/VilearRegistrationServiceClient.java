package it.tcgroup.vilear.course.Client.ServiceClient;

import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.base.Payload.Response.RolePersonResponse;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;

public interface VilearRegistrationServiceClient {

    /**
     * Recupera il Ruolo associato alla Persona
     * @param studentPartecipationModel contiene i dati della Partecipazione
     */
    RolePersonResponse getRolePerson(StudentPartecipationModel studentPartecipationModel);

    /**
     * Recupera la Persona
     * @param personId id della Persona
     */
    PersonResponse getPerson(Integer personId);

}
