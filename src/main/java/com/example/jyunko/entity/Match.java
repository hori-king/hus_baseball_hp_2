package com.example.jyunko.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "match")
@Data
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "match_date")
	private LocalDate matchDate;
	private String opponent;
	@Column(name = "our_score")
	private Integer ourScore;
	@Column(name = "opponent_score")
	private Integer opponentScore;
	// 例: "勝利", "敗北", "引き分け" などを文字列で保存
	private String result;
	//試合会場
	private String venue;
	// 長いコメントを保存できるようにTEXT型を指定
	@Column(columnDefinition = "TEXT")
	private String comment;
	private String photo;

}
