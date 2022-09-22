package accountManagement.dataAccess;

import java.util.Optional;

import accountManagement.jwt.ApplicationUser;

public interface ApplicationUserDao {

	Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
