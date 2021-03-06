package pe.sernanp.simrac.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.sernanp.simrac.model.LoginModel;
import pe.sernanp.simrac.repository.LoginRepository;


@Service("userDetailsService")
public class UserServiceImpl  implements UserDetailsService {

	@Autowired	
	private LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginModel user = loginRepository.validate(username);
		if (user == null) {
		throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}			
		
		UserDetails userDetails;
		
		userDetails = new org.springframework.security.core.userdetails.User(user.getUser(),
				user.getPassword(), getAuthorityUser());
		
		/*if(user.getTipo()==1) {
			 userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
					user.getPassword(), getAuthorityUser());
		}else {
			 userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
						user.getPassword(), getAuthorityAdmin());
		}*/
		
		return userDetails;
	}
	
	private List<SimpleGrantedAuthority> getAuthorityAdmin() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	private List<SimpleGrantedAuthority> getAuthorityUser() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
}
