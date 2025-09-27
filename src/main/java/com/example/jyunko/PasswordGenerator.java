package com.example.jyunko;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "";// ここに生成したいパスワードを入力
		String encoderPassword = encoder.encode(rawPassword);

		System.out.println("生成されたパスワード" + encoderPassword);
	}
}
