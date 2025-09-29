package com.example.jyunko.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	// ユーザーID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// ユーザー名
	private String username;
	// パスワード
	private String password;
	// 役割（例: "ADMIN", "USER" など）
	private String role;
}
