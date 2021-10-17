package pe.sernanp.simrac;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	@Value("${security.jwt.ExpirationDateInMs}")
	private Integer expirationDateToken;
	
	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private AuthenticationManager authenticationManager;	
	
	//INI-CAMBIO PARA SPRING BOOT 2
	//https://stackoverflow.com/questions/49197111/migration-to-spring-boot-2-security-encoded-password-does-not-look-like-bcrypt
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	//FIN-CAMBIO PARA SPRING BOOT 2

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		//INI-CAMBIO PARA SPRING BOOT 2 (secret(bcrypt.encode(clientSecret))
				/*configurer.inMemory().withClient(clientId).secret(bcrypt.encode(clientSecret)).authorizedGrantTypes(grantType)
						.scopes(scopeRead, scopeWrite).resourceIds(resourceIds).accessTokenValiditySeconds(20)
						.refreshTokenValiditySeconds(0);*/
				configurer.inMemory().withClient(clientId)
				.secret(bcrypt.encode(clientSecret))
				.authorizedGrantTypes(grantType)
				.scopes(scopeRead, scopeWrite)
				.resourceIds(resourceIds)
				.accessTokenValiditySeconds(Integer.MAX_VALUE)
				.refreshTokenValiditySeconds(0);
				//FIN-CAMBIO PARA SPRING BOOT 2
				
		/*configurer.inMemory().withClient(clientId).secret(clientSecret).authorizedGrantTypes(grantType)
				.scopes(scopeRead, scopeWrite).resourceIds(resourceIds).accessTokenValiditySeconds(30000)
				.refreshTokenValiditySeconds(0);*/
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(tokenStore)
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(enhancerChain)
		.authenticationManager(authenticationManager);
		
		endpoints.tokenServices(customTokenServices());
		
		//.exceptionTranslator(loggingExceptionTranslator());		//AÑADIDO PARA DEBUG	
	}
	
	public AuthorizationServerTokenServices customTokenServices()
	{
		 //TokenStore tokenStore = new JdbcTokenStore(this.dataSource);
		  DefaultTokenServices tokenServices = new DefaultTokenServices();
		  //tokenServices.setReuseAccessToken(reuseAccessToken);
		  tokenServices.setTokenStore(tokenStore);
		  tokenServices.setSupportRefreshToken(true);
		  tokenServices.setAccessTokenValiditySeconds(expirationDateToken);
		  //tokenServices.setClientDetailsService(clientDetailsService);
		  return tokenServices;
	}
	
}
