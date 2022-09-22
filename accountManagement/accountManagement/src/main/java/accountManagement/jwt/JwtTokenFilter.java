package accountManagement.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import accountManagement.services.ApplicationUserService;


public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private ApplicationUserService userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			 String jwtToken = extractJwtFromRequest(request);
			 if(StringUtils.hasText(jwtToken) && tokenManager.validateToken(jwtToken)) {
				 String username= tokenManager.getUsernameFromToken(jwtToken);
				 UserDetails user = userDetailsService.loadUserByUsername(username);
				 
				 if(user != null) {
					 UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
	                            user.getAuthorities());
					 
					 auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                    SecurityContextHolder.getContext().setAuthentication(auth);
				 }
			 }
			
		}catch (Exception e) {
			return;
		}
		 filterChain.doFilter(request, response);
	}
	
	public String extractJwtFromRequest(HttpServletRequest request) {
		
	    String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring("Bearer".length() + 1);
        return null;
		
	}

}
