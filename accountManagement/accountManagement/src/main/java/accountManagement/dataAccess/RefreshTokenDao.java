package accountManagement.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import accountManagement.entities.RefreshToken;

public interface RefreshTokenDao extends JpaRepository<RefreshToken, Integer>{
	 RefreshToken findByUserId(int userId);
}
