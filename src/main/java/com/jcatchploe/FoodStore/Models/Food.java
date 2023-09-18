package com.jcatchploe.FoodStore.Models;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Food {

	//Attributes variable
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Length(min = 3, max = 50, message = "  type must contain between 3 and 20 characters")
	private String type;
	
	@NotNull
	@Length(min = 2, max = 50, message = "  name must contain between 2 and 20 characters")
	private String name;
	
	@NotNull
	private double price;
	@NotNull
	@Length(min = 5, max = 50, message = "description must contain between 5 and 20 characters")
	private String description;
	
	@NotNull
	private int kcals;
	
	@NotNull
	@Length(min = 5, max = 20, message = "imageUrl must contain between 5 and 20 characters")
	private String imageUrl;
	
}
