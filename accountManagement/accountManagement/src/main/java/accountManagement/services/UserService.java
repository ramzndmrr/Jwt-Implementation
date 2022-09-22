package accountManagement.services;



import accountManagement.dataAccess.ApplicationUserDao;
import accountManagement.entities.User;

public interface UserService extends ApplicationUserDao {
	User getOneUserByUserName(String username);
	User saveOneUser(User user);
}
