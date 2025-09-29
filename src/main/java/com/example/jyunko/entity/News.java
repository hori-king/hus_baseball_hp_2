package com.example.jyunko.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "news")
@Data
public class News {
	// ニュースID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 投稿日
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "posted_date")
	private LocalDate postedDate;
	// タイトル
	private String title;
	// 内容
	private String content;
	// カテゴリ
	private String category;
	// ニュースの写真のURLやパスを保存
	private String photo;

}
