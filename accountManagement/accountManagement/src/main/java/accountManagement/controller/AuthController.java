package accountManagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import accountManagement.entities.AuthDto;
import accountManagement.entities.User;
import accountManagement.jwt.TokenManager;
import accountManagement.services.RefreshTokenService;
import accountManagement.services.UserService;

@RestController
@RequestMapping("/auth")	
public class AuthController {
	
	 private AuthenticationManager authenticationManager;
	 private TokenManager tokenManager;
	 private UserService userService;
	 private RefreshTokenService refreshTokenService;

	public AuthController(AuthenticationManager authenticationManager, TokenManager tokenManager,
			UserService userService, RefreshTokenService refreshTokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenManager = tokenManager;
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
	}

	
	//=========================  LOGİN  ==========================================
	
	@PostMapping("/login")
	public AuthDto login(@RequestBody accountManagement.entities.UserRequest loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getUserName(), 
				loginRequest.getPassword());
		
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = tokenManager.generateJwtToken(auth);
		User user = userService.getOneUserByUserName(loginRequest.getUserName());
		
		
		AuthDto authResponse = new AuthDto();
		authResponse.setAccessToken("Bearer " + jwtToken);
		authResponse.setUserName(user.getUserName());
		authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user).getData());
		authResponse.setUserId(user.getId());
		authResponse.setMessage("Successed.");
		authResponse.setFirstName(user.getFirstName());
	    authResponse.setLastName(user.getLastName());
		
	    return authResponse;
	}
	
	//======================  REGISTER  ===============================================
	
	
	@PostMapping("/register")
	public ResponseEntity<AuthDto> register(@RequestBody accountManagement.entities.UserRequestForRegister  registerRequest){
		AuthDto authResponse = new AuthDto();
		
		 // User exists?
        if (userService.getOneUserByUserName(registerRequest.getUserName()) != null) {
            authResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        
     // User creating...
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUserName(registerRequest.getUserName());
        user.setPassword(registerRequest.getPassword());
        
        userService.saveOneUser(user);
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        		registerRequest.getUserName(),
        		registerRequest.getPassword());
        
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = tokenManager.generateJwtToken(auth);
        
        authResponse.setMessage("User successfully registered.");
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user).getData());
        authResponse.setUserId(user.getId());
        authResponse.setUserName(user.getUserName());
        authResponse.setFirstName(user.getFirstName());
        authResponse.setLastName(user.getLastName());
        
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
