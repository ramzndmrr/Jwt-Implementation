package accountManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import accountManagement.dataAccess.ApplicationUserDao;

@Service
public class ApplicationUserService  implements UserDetailsService{
	
	private ApplicationUserDao applicationUserDao;
	
	

	public ApplicationUserService(ApplicationUserDao applicationUserDao) {
		this.applicationUserDao = applicationUserDao;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return applicationUserDao
				.selectApplicationUserByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("user not found"));
	}

}
