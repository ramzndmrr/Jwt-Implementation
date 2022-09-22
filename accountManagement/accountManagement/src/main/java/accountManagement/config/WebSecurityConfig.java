package accountManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


import accountManagement.jwt.JwtAuthenticationEntryPoint;
import accountManagement.jwt.JwtTokenFilter;
import accountManagement.services.ApplicationUserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends 	WebSecurityConfigurerAdapter {
	
	private PasswordEncoder passwordEncoder;
	private ApplicationUserService applicationUserService;
	private JwtAuthenticationEntryPoint handler;
	
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }
	
	
	
	
	/*
	 * @Bean public JwtAuthenticationFilter jwtAuthenticationFilter() { return new
	 * JwtAuthenticationFilter(); }
	 */
	
	
	public JwtTokenFilter jwtAuthenticationFilter() {
		return  new JwtTokenFilter();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
			http
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(handler)
			.and()
			.cors()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests().antMatchers("/auth/login").permitAll()
			.anyRequest()
			.authenticated();
	}
	
	
	
	
	  @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
              auth.authenticationProvider(daoAuthenticationProvider());
      }
	
	private AuthenticationProvider  daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

}