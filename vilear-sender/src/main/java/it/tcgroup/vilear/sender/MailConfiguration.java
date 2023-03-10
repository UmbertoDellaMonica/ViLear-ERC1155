package it.tcgroup.vilear.sender;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.util.Properties;
import java.util.logging.Logger;

@Configuration
public class MailConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPreferNestedProperties(false);
        return mapper;
    }

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.protocol}")
    private String protocol;


    /**
     * Setta le impostazioni principali per poter inviare la mail
     * -port
     * -host
     * -username
     * -password
     * -protocol
     * -auth
     * -debug
     */
    @Bean
    public JavaMailSender getJavaMailSender(){
        // Setting Component
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(Integer.parseInt(port));
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        // Setting Properties
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol",protocol);
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.debug",true);
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }


    @Bean
    public Logger logger(){
        return Logger.getGlobal();
    }

        @Primary
        @Bean
        public FreeMarkerConfigurationFactoryBean getFreeMarkerConfig() {
            FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
            bean.setTemplateLoaderPath("classpath:/template/");

            return bean;
        }

}
