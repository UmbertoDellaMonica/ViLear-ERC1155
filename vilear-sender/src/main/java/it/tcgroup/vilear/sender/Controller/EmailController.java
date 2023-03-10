package it.tcgroup.vilear.sender.Controller;

import freemarker.template.TemplateException;
import it.tcgroup.vilear.base.Payload.Request.EmailRequest;
import it.tcgroup.vilear.sender.Model.Email;
import it.tcgroup.vilear.sender.Service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper mapper;

    /*
    @PostMapping("/sendMail")
    public void sendSimpleMail(@RequestBody EmailRequest emailRequest){
        Email email = mapper.map(emailRequest, Email.class);
        emailService.sendSimpleMail(email);
    }

    @PostMapping("/sendMailWithAttachMent")
    public void sendEmailWithAttachMent(@RequestBody EmailRequest emailRequest){
        Email email = mapper.map(emailRequest, Email.class);
        emailService.sendMailWithAttachment(email);
    }*/


    /**
     * Invita l'email alla Persona
     * @param emailRequest contiene i dati della persona a cui inviare l'email
     * @return
     */
    @PostMapping("/invite")
    public ResponseEntity<Boolean> sendInviteEmail(
            @RequestBody EmailRequest emailRequest
    ) throws TemplateException, IOException {
        Email email = mapper.map(emailRequest, Email.class);
        email.setSubject(" Dear User, SIGN UP on Vilear! ");
        Boolean flag = emailService.sendInviteEmail(email);
        return new ResponseEntity<>(flag, HttpStatus.OK);
    }
}
