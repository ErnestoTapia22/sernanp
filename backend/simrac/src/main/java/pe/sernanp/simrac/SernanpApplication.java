package pe.sernanp.simrac;

import javax.crypto.SecretKey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SpringBootApplication
public class SernanpApplication extends SpringBootServletInitializer {

	public static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("inicio de aplicación");
		return application.sources(SernanpApplication.class);		
	}	
	
	public static void main(String[] args) {
		System.out.println("inicio de aplicación");
		SpringApplication.run(SernanpApplication.class, args);
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				//.antMatchers(HttpMethod.POST).permitAll().antMatchers(HttpMethod.GET).permitAll().antMatchers(HttpMethod.PUT).permitAll() //comentar para producción
				.antMatchers("/api/user/validate/{id}").permitAll() //descomentar para producción
				.anyRequest().authenticated();
			http.cors().and();					
		}
		
	}
}
