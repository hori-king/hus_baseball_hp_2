package com.example.jyunko.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Value("${ADMIN_USERNAME}")
	private String username;

	@Value("${ADMIN_PASSWORD}")
	private String password;

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
						.defaultSuccessUrl("/")
						// ログインページのURLを誰でもアクセスできるように設定
						.permitAll())
				.logout(logout -> logout
						// ログアウト成功後のリダイレクト先を指定
						.logoutSuccessUrl("/"));

		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username(username)
				.password(password)
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);

	}
}
