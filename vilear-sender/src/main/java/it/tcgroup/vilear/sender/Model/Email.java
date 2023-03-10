package it.tcgroup.vilear.sender.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Email {

    String to;// Destinatario
    String from;// Soggetto
    String subject; // Oggetto
    String content;// Contenuto
    private Map<String, Object> model; // Contenuto Html
}
