package com.jcatchploe.FoodStore.Dto;

import com.jcatchploe.FoodStore.Models.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
	
	@NotNull
	private String username;
	@NotNull
	private String password;
	
	private Role role;

}
