package it.tcgroup.vilear.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VilearRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(VilearRegistrationApplication.class, args);
	}

}
