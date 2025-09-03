package com.example.jyunko.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "inquiry")
@Data
public class Inquiry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String subject;
	@Column(columnDefinition = "TEXT")
	private String content;
	private Integer status; // 0:未対応, 1:対応中, 2:対応済み
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
