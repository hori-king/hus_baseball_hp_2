package com.example.jyunko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUserName(String name);
}
