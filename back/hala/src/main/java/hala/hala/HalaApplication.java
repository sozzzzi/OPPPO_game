package hala.hala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HalaApplication.class, args);
	}
}
