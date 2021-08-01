package palvelinohjelmointi.autonlampimaksi.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import palvelinohjelmointi.autonlampimaksi.models.User;
import palvelinohjelmointi.autonlampimaksi.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Ei tälläistä käyttäjää: " + username);
		}
		
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPasswordHash(),
				true,
				true,
				true,
				true,
				Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
	}

}
