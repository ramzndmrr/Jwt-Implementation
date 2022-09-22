package accountManagement.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import accountManagement.entities.User;

public interface UserDao  extends JpaRepository<User, Integer>{
	 User findByUserName(String username);
}
