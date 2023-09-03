package com.jcatchploe.FoodStore.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jcatchploe.FoodStore.Dto.AuthResponse;
import com.jcatchploe.FoodStore.Repository.UserRepository;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.jcatchpole.FoodStore"})
public class ApplicationConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Bean
	public UserDetailsService userDetailsService() {
			return new UserDetailsService() {
				
				@Override
				public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
					// TODO Auto-generated method stub
					return (UserDetails) userRepository.findByUsername(username).get();
				}
			};
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig) throws Exception {
		return authenticationConfig.getAuthenticationManager();
	}
	
	@Bean
	public AuthResponse authenticationResponse(){
		return new AuthResponse();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(16);
	}

	
}
