package com.jcatchploe.FoodStore.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcatchploe.FoodStore.Models.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
	Optional<Food> findByName(String name);
	void deleteByName(String name);
	List<Food> findAllByTypeEquals(String type);
	

}
