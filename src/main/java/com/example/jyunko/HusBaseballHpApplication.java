package com.example.jyunko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class HusBaseballHpApplication {

	public static void main(String[] args) {
		// .envファイルの内容を環境変数として読み込む
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(HusBaseballHpApplication.class, args);
	}

}