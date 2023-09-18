package com.jcatchploe.FoodStore.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jcatchploe.FoodStore.Dto.AuthRequest;
import com.jcatchploe.FoodStore.Dto.AuthResponse;
import com.jcatchploe.FoodStore.Models.User;


@Service
public interface UserService {
	// User service methods
	public AuthResponse addNewUser(AuthRequest request);
	public User getUserById(long id);
	public User getUserByUsername(String username);
	public void deleteUserById(long id);
	public void deleteUserByUsername(String username);
	public List<User> getAllUsers();
	public AuthResponse authenticateUser(AuthRequest request);
	public AuthResponse addNewAdminUser(AuthRequest request);
	public User updateUserDetailsById(User user);
}
