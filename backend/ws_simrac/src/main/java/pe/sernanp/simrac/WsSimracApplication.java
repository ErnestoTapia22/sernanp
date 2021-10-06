package pe.sernanp.simrac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WsSimracApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("inicio de aplicación");
		return application.sources(WsSimracApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WsSimracApplication.class, args);
	}

}
