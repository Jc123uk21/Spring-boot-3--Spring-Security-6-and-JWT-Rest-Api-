package com.jcatchploe.FoodStore.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jcatchploe.FoodStore.Models.Food;

@Service
public interface FoodServcie {
	
	public Food addNewFood(Food food);
	public Food getFoodByName(String name);
	public List<Food> getFoodsByType(String type);
	public Food getFoodById(long id);
	public void deleteFoodById(long id);
	public void deleteFoodByName(String name);
	public List<Food> getAllFoods();

}
