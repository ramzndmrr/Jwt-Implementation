package accountManagement.services;

import java.util.Optional;

import accountManagement.dataAccess.UserDao;
import accountManagement.entities.User;
import accountManagement.jwt.ApplicationUser;

public class UserServiceManager implements UserService {
	
	private UserDao userDao;

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getOneUserByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public User saveOneUser(User user) {
		return userDao.save(user);
	}

}
