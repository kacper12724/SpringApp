package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springapp.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

}
