package com.jcatchploe.FoodStore.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcatchploe.FoodStore.Dto.AuthRequest;
import com.jcatchploe.FoodStore.Dto.AuthResponse;
import com.jcatchploe.FoodStore.Models.Role;
import com.jcatchploe.FoodStore.Models.User;
import com.jcatchploe.FoodStore.Repository.UserRepository;

import jakarta.validation.constraints.NotNull;
@Service
public class UserServiceImpl implements UserService {
	
	//Bring in repository to interact with database
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthResponse response;

	@Override
	public User getUserById(long id) {
		// get user details from the database
		return this.userRepository.findById(id).get();
	}

	@Override
	public User getUserByUsername(String username) {
		// get user details by their username value
		return this.userRepository.findByUsername(username).get();
	}

	@Override
	public void deleteUserById(long id) {
		// delete a user by their id value
		
		this.userRepository.deleteById(id);
	}

	@Override
	public void deleteUserByUsername(String username) {
		// delete user by their username value
		this.userRepository.deleteByUsername(username);
	}

	@Override
	public List<User> getAllUsers() {
		// get a list of all users held within the database
		return this.userRepository.findAll();
	}

	@Override
	public AuthResponse addNewUser(AuthRequest request) {
		// create new user object from request object to add to database
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(this.passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.ROLE_USER);
		
		userRepository.save(user);
		
		response.setToken(jwtService.generateToken(user));
		
		return response;
	}

	@Override
	public AuthResponse authenticateUser(AuthRequest request) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), 
						request.getPassword())
				);
		User user = (User)userRepository.findByUsername(request.getUsername()).get();
		
        response.setToken(jwtService.generateToken((@NotNull UserDetails) user));
		
		return response;
	}

	//Add new admin user
	public AuthResponse addNewAdminUser(AuthRequest request) {
		User adminUser = new User();
		adminUser.setUsername(request.getUsername());
		adminUser.setPassword(this.passwordEncoder.encode(request.getPassword()));
		adminUser.setRole(Role.ROLE_ADMIN);
		
		userRepository.save(adminUser);
		
		response.setToken(jwtService.generateToken((@NotNull UserDetails) adminUser));
		
		return response;
	}
	
	//Update user details
	public User updateUserDetailsById(User user){
		User userDetails = this.getUserById(user.getId());
		if(userDetails != null) {
			userDetails.setPassword(passwordEncoder.encode(user.getPassword()));
			userDetails.setUsername(user.getUsername());
			//Delete users current details
			this.userRepository.deleteById(user.getId());
			//store users new details
			this.userRepository.save(userDetails);
			return userDetails;	
		}
		return user;
	}
	
	
}
