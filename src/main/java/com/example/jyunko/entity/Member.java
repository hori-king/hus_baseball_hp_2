package com.example.jyunko.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.Data;

@Entity
@Table(name = "member")
@Data
public class Member {
	// 選手ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 選手名
	private String name;
	// 学年
	@Min(value = 1, message = "学年は1以上の整数で入力してください")
	@Max(value = 6, message = "学年は6以下の整数で入力してください")
	private String grade;
	// 背番号
	@Min(value = 0, message = "背番号は0以上の整数で入力してください")
	private Integer number;
	// ポジション
	private String position;
	// 出身校
	@Column(name = "alma_mater")
	private String almaMater;
	// 学部
	private String faculty;
	// 学科
	private String department;
	// 写真のパス
	private String photo;
}
