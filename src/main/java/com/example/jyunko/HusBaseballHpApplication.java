package com.example.jyunko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class HusBaseballHpApplication {

	public static void main(String[] args) {
		// .envファイルの内容を環境変数として読み込む
		Dotenv dotenv = Dotenv.load();
		System.setProperty("MYSQL_PASSWORD", dotenv.get("MYSQL_PASSWORD"));
		SpringApplication.run(HusBaseballHpApplication.class, args);
	}

}