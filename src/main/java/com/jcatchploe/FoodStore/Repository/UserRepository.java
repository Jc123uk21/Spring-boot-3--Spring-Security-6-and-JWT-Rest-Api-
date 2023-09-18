package com.jcatchploe.FoodStore.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.jcatchploe.FoodStore.Models.User;


public interface UserRepository extends JpaRepository<User, Long> {
	//custom query methods
	Optional<User> findByUsername(String username);
	void deleteByUsername(String username);
}
