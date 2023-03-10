package it.tcgroup.vilear.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VilearSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(VilearSenderApplication.class, args);
	}

}
