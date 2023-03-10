package it.tcgroup.vilear.sender.Service;

import freemarker.template.TemplateException;
import it.tcgroup.vilear.sender.Model.Email;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.Map;

public interface EmailService {


    /**
     * Invia l'email alla persona
     * @param mail contiene i dati per la Mail
     * Mail Invite
     */
    Boolean sendInviteEmail(Email mail) throws TemplateException, IOException;

}
