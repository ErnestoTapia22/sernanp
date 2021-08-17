package pe.sernanp.simrac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("inicio de aplicación");
		SpringApplication.run(Application.class, args);
	}

}
