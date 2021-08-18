package pe.sernanp.simrac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties.ConfigureAction;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SernanpApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		System.out.println("inicio de aplicación");
		return builder.sources(SernanpApplication.class);		
	}
	
	
	public static void main(String[] args) {
		System.out.println("inicio de aplicación");
		SpringApplication.run(SernanpApplication.class, args);
	}
	
}
