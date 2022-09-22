package accountManagement.jwt;


import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenManager {
	
	
	 private final SecretKey secretKey;
	 private final JwtConfig config;
	 
	 
	   public String generateJwtToken(Authentication  auth) {
		   
		    ApplicationUser userDetails =(ApplicationUser) auth.getPrincipal();
	        return Jwts.builder()
	                .setSubject(userDetails.getUsername())
	                .claim("authorities", userDetails.getAuthorities())
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(new Date().getTime() + config.getExpiresIn()))
	                .signWith(secretKey)
	                .compact();
	    }
	   
	   
	   public String  getUsernameFromToken(String token) {
		  Claims claims = getJwtBody(token);
		  return claims.getSubject();
	   }
	   
	   
	   public boolean validateToken(String token) {
		   try {
			   getJwtBody(token);
			   return !isTokenExpired(token);  
		   }catch (MalformedJwtException e) {
			return false;
		   }catch (ExpiredJwtException e) {
			   return false;		
		   }catch (UnsupportedJwtException e) {
			return false;
		}catch (IllegalArgumentException e) {
			return false;
		}
	   }
	   
	   private Claims getJwtBody(String token){
		  return  Jwts
				  .parser()
				  .setSigningKey(secretKey)
				  .parseClaimsJws(token)
				  .getBody();
	   }
	   
	   private boolean isTokenExpired(String token) {
		   Date expiration = getJwtBody(token).getExpiration();
		   return expiration.before(new Date());
	   }
	   
	   
	   
	   
	   
	   
}
