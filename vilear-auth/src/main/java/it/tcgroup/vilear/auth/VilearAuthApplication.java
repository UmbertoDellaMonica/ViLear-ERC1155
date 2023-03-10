package it.tcgroup.vilear.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VilearAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(VilearAuthApplication.class, args);
	}

}
