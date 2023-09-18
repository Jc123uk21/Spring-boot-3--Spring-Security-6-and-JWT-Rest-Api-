package com.jcatchploe.FoodStore.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcatchploe.FoodStore.Models.Food;
import com.jcatchploe.FoodStore.Repository.FoodRepository;

@Service
public class FoodServiceImpl implements FoodServcie {

	@Autowired
	private FoodRepository foodRepository;
	

	@Override
	public Food addNewFood(Food food) {
		// Save new food to database
		return foodRepository.save(food);
	}

	@Override
	public Food getFoodByName(String name) {
		// get food from the database by its name value
		return foodRepository.findByName(name).get();
	}

	@Override
	public Food getFoodById(long id) {
		// get food from the database by its id value
		return foodRepository.findById(id).get();
	}

	@Override
	public void deleteFoodById(long id) {
		// delete food from database by its id value
		foodRepository.deleteById(id);
	}

	@Override
	public void deleteFoodByName(String name) {
		// delete food from database by its name value
		foodRepository.deleteByName(name);
	}

	@Override
	public List<Food> getAllFoods() {
		// get a list of all food held within the databse
		return foodRepository.findAll();
	}

	@Override
	public List<Food> getFoodsByType(String type) {
		// TODO Auto-generated method stub
		return foodRepository.findAllByTypeEquals(type);
	}

}
