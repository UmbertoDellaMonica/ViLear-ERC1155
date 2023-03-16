package it.tcgroup.vilear.sender.Service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import it.tcgroup.vilear.sender.Model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String inviteEmailTemplate="invite.html";
    private static final String inviteEmailAcceptTemplate="email.flth";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Logger logger;

    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private  Configuration configuration;


    private String getEmailContent(Email email) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        model.put("email", email);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(inviteEmailTemplate));

        configuration.getTemplate(file.getPath()).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    @Async
    @Override
    public Boolean sendInviteEmail(Email mail) throws TemplateException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(mail.getTo());
            mail.setContent( getEmailContent(mail) ); //Inserisce tutti i dati di un determinato oggetto
            mimeMessageHelper.setText(mail.getContent(), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            logger.info(" Email was sent Successfully...");
            return Boolean.TRUE;
        } catch (MessagingException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
