package pe.sernanp.simrac;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration//indico que Spring detecte esta clase como una clase de configuración en la que instancias beans en el core container de Spring
@EnableWebSecurity // se habilita ya que estamos trbaajando con Spring Security poara el tema de servicios web.para habilitar la seguridad en servicios web, comentado para deshabilitar la seguridad.
@EnableGlobalMethodSecurity(prePostEnabled = true)//Para que toda la aplicación esté protgegida por Spring Security
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//WebSecurityConfigurerAdapter es una clase de spring securituy
	//Nosotros extendemos la clase para sobre escribir algunos metodos
	
	
	//un @Bean lo que hace es definir una clase dentro del core container de Spring, para luego inyectarla mediante un @Autowired
	
	//estas 3 variables, son las del aplication.properties
	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.security-realm}")
	private String securityRealm;
	
	
	
	@Autowired	
	private DataSource dataSource;
	
	@Autowired	
	private UserDetailsService userDetailsService;//es una clase propia de Spring Security, para proveer donde están los usuariuos y donde están los roles
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bcrypt);//me permite definir donde se encuentran todos los recursos de los uuarios, tablas, roles, etc.
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http		
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//tipo stateless, ya que estamos en una aplicación partida, donde se envía data y se recibe data
        .and()
        .httpBasic()
        .realmName(securityRealm)        
        .and()
        .csrf()//cross site request forgery, SPRING security nos protege frente a esto, si no lo deshabilitamos, SPRING security generaría un token bajo este mecanismo, pero como nosotros tendremos JWT, lo deshabilitaremos, usualmente se utliza cuando se trrabaja con SPRING MVC
        .disable();
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);		
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		//return new JwtTokenStore(accessTokenConverter()); // es un espacio en memoria temporal, donde están nuestros tokens
		return new JdbcTokenStore(this.dataSource);//se almacenará en la base de datos, se hace un JdbcTokenStore, es el termino que usa Spring para indicar que es hacia la base de datos
		//el datasource es la inyeción que se agrego arriba para utilizarlo
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);	
		defaultTokenServices.setReuseRefreshToken(false);	
		return defaultTokenServices;
	}
}
