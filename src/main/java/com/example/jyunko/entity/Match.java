package com.example.jyunko.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "matches")
@Data
public class Match {
	// 試合ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 試合日
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "match_date")
	private LocalDate matchDate;
	// 対戦相手
	private String opponent;
	//得点
	@Column(name = "our_score")
	@Min(value = 0, message = "得点は0以上の整数で入力してください。")
	private Integer ourScore;
	//失点
	@Column(name = "opponent_score")
	@Min(value = 0, message = "失点は0以上の整数で入力してください。")
	private Integer opponentScore;
	// 例: "勝利", "敗北", "引き分け" などを文字列で保存
	private String result;
	//試合会場
	private String venue;
	// 長いコメントを保存できるようにTEXT型を指定
	@Column(columnDefinition = "TEXT")
	private String comment;
	// 試合の写真のURLやパスを保存
	private String photo;

}
