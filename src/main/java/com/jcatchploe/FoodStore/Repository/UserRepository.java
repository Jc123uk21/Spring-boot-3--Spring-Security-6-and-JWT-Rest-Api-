package com.jcatchploe.FoodStore.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcatchploe.FoodStore.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//custom query methods
	Optional<User> findByUsername(String username);
	void deleteByUsername(String username);
}
