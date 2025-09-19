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
	// 問い合わせID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 名前
	private String name;
	// メールアドレス
	private String email;
	// 件名
	private String subject;
	// 内容
	@Column(columnDefinition = "TEXT")
	private String content;
	// ステータス（0:未対応, 1:対応中, 2:対応済み）
	private Integer status;
	// 作成日時
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
