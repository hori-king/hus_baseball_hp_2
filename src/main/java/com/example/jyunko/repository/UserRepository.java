package com.example.jyunko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jyunko.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String name);
}
