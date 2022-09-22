package accountManagement.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import accountManagement.core.ApiResponse;
import accountManagement.dataAccess.RefreshTokenDao;
import accountManagement.entities.RefreshToken;
import accountManagement.entities.User;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;



@Service
public class RefreshTokenManager implements RefreshTokenService{
	
	  @Value("${application.jwt.refresh.token.expires.in}")
	    Long expireSeconds;
	  
	  private RefreshTokenDao refreshTokenDao;
	  
		public RefreshTokenManager(RefreshTokenDao refreshTokenDao) {
			this.refreshTokenDao = refreshTokenDao;
		}

	@Override
	public ApiResponse<String> createRefreshToken(User user) {
		RefreshToken  token = refreshTokenDao.findByUserId(user.getId());
		if(token==null) {
			token=new RefreshToken();
			token.setUser(user);
		}
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
		refreshTokenDao.save(token);
		return ApiResponse.default_OK(token.getToken());
		
	}

	public ApiResponse<Boolean> isRefreshExpired(RefreshToken token) {
        Boolean isExpired = token.getExpiryDate().before(new Date());
        return ApiResponse.default_OK(isExpired);
    }

	 public ApiResponse<RefreshToken> getByUser(int userId) {
	        return ApiResponse.default_OK(refreshTokenDao.findByUserId(userId));
	    }

}
