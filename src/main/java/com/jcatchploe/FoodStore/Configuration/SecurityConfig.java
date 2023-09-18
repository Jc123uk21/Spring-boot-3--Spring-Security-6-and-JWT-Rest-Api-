package com.jcatchploe.FoodStore.Configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	
	/**@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("/**"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT", "DELETE", "OPTIONS", "HEAD"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}*/
	
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests((authorizeHttpRequests)->
			authorizeHttpRequests
				// accessible to everyone
				.requestMatchers("/auth/user/register", "/auth/user/login", "/food/all", "/food/{id}", "/food/{name}",
						 "/food/search/{type}").permitAll()
				
				//only accessible to admin users
				.requestMatchers("/admin/{id}", "/food/add", "/food/delete/{id}",  "/user/all", "/user/update/{id}",
						"/auth/admin/add","/user/delete/{id}", "/food/update/{id}").hasAuthority("ROLE_ADMIN")
				
				// accessible to both registered users and admin users
				.requestMatchers("/user/{id}", "/user/update/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				
				// auth required for all other requests
				.anyRequest().authenticated()
		)
		//Set session management to stateless
		.sessionManagement((sessionManagement)->
				sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		)	
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
	}
}
