package com.jcatchploe.FoodStore.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcatchploe.FoodStore.Dto.AuthRequest;
import com.jcatchploe.FoodStore.Dto.AuthResponse;
import com.jcatchploe.FoodStore.Services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	private UserService userService;
	
	//add new user (refine to add checks)
	@PostMapping("/user/register")
	public ResponseEntity<AuthResponse> addUser(@RequestBody AuthRequest request){
		return ResponseEntity.ok(userService.addNewUser(request));
	}
	
	//Login user
	@PostMapping("/user/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
		System.out.println("controller accessed");
		return ResponseEntity.ok(userService.authenticateUser(request));
	}
}
