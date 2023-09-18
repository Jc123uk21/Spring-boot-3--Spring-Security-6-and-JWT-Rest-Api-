package com.jcatchploe.FoodStore.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcatchploe.FoodStore.Models.Food;
import com.jcatchploe.FoodStore.Services.FoodServiceImpl;



@RestController
@RequestMapping(path ="/food")
public class FoodController {
	
	@Autowired
	private FoodServiceImpl foodService;
	
	//Retrieve a list of all foods 
	@GetMapping("/all")
	public ResponseEntity<List<Food>> getAllFoods(){
		List<Food> foods = this.foodService.getAllFoods();
		return new ResponseEntity<>(foods, HttpStatus.OK);
	}

	//Retrieve a single food by it id value
	@GetMapping("/{id}" )
	public ResponseEntity<Food> findFoodById(@PathVariable(value = "id") Long id){
		Food food = this.foodService.getFoodById(id);
		return new ResponseEntity<>(food,HttpStatus.OK);
	}
	
	@GetMapping("/search/{type}")
	public ResponseEntity<List<Food>> findByFoodType(@PathVariable(value = "type") String type){
		List<Food> foods = this.foodService.getFoodsByType(type);
		return new ResponseEntity<>(foods, HttpStatus.OK);
	}

	//Add a new food to the database, only accessible to admin users
	@PostMapping("/add")
	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	public ResponseEntity<Food> addNewFood(@RequestBody Food food){
		Food newFood = food;
		this.foodService.addNewFood(newFood);
		return new ResponseEntity<>(newFood,HttpStatus.CREATED);
	}
	
	//Delete food from database by its id value, only accessible to admin user
	@PostMapping("delete/{id}")
	@PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
	public ResponseEntity<?> deleteFoodById(@PathVariable(value = "id") long id){
		this.foodService.deleteFoodById(id);
		return new ResponseEntity<>("Food Deleted with id : " + id,HttpStatus.OK);
	}
	

}
