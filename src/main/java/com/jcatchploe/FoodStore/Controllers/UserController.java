package com.jcatchploe.FoodStore.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcatchploe.FoodStore.Models.Role;
import com.jcatchploe.FoodStore.Models.User;
import com.jcatchploe.FoodStore.Services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	//Get User by id value accessible to admin and registered users only their id matches the request id
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority({'ROLE_USER','ROLE_ADMIN'})")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id){
		
		//Get Current if a registered user's id matches the id being requested or the request is from an admin user
		User currentUser = this.userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(currentUser.getId() == id || currentUser.getRole()== Role.ROLE_ADMIN){
			User user = this.userService.getUserById(id);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	//Get a list of all users only accessible to admin users
	@GetMapping("/all")
	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = this.userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	//Delete user by their id value only accessible to admin users
	@PostMapping("/delete/{id}")
	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	public ResponseEntity<?> deleteUser(@PathVariable(value ="id") long id){
		this.userService.deleteUserById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Update user details accessible to admin and registered users
	@PostMapping("/update/{id}")
	@PreAuthorize("hasAuthority({'ROLE_USER','ROLE_ADMIN'})")
	public ResponseEntity<User> updateUserDetails(@RequestBody User user){
		User updatedUser = this.userService.updateUserDetailsById(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	

}
