package com.example.jyunko.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.jyunko.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
				// CSSや画像などの静的リソース
				// トップページ、お知らせ、部員紹介、試合結果
				// お問い合わせフォームは誰でもアクセスできるように設定
				.requestMatchers("/css/**", "/images/**").permitAll()
				.requestMatchers("/", "/news/**", "/members/**", "/matches/**").permitAll()
				// 上記以外のすべてのリクエストは、認証（ログイン）が必要
				.requestMatchers("/inquiry").permitAll()
				.anyRequest().authenticated()).formLogin(login -> login
						// ログインページのURLを指定
						.loginPage("/login")
						// ログイン成功後のリダイレクト先を指定
						.defaultSuccessUrl("/admin")
						// ログインページのURLを誰でもアクセスできるように設定
						.permitAll())
				.logout(logout -> logout
						// ログアウト成功後のリダイレクト先を指定
						.logoutSuccessUrl("/"));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
