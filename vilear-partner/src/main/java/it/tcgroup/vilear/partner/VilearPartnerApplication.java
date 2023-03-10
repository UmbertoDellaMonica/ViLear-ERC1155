package it.tcgroup.vilear.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class VilearPartnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VilearPartnerApplication.class, args);
    }

}
