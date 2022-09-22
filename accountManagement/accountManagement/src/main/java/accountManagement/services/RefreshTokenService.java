package accountManagement.services;

import accountManagement.core.ApiResponse;
import accountManagement.entities.RefreshToken;
import accountManagement.entities.User;

public interface RefreshTokenService {
	
	ApiResponse<String> createRefreshToken(User user);
	ApiResponse<Boolean> isRefreshExpired(RefreshToken refreshToken);
	ApiResponse<RefreshToken> getByUser(int userId);
}
