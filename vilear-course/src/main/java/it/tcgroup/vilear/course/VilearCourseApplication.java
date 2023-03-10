package it.tcgroup.vilear.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VilearCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(VilearCourseApplication.class, args);
	}

}
