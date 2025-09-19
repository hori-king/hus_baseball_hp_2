package com.example.jyunko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jyunko.entity.User;
import com.example.jyunko.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	// ユーザー名でユーザーを検索し、UserDetailsオブジェクトを返す
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// ユーザー名でユーザーを検索
		User user = userRepository.findByUsername(username);
		// ユーザーが見つからない場合、例外をスロー
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		// ユーザー情報を基にUserDetailsオブジェクトを作成して返す
		return new org.springframework.security.core.userdetails.User(
				//ユーザ名
				user.getUsername(),
				//パスワード
				user.getPassword(),
				//権限（ROLE_を付与）
				AuthorityUtils.createAuthorityList("ROLE_" + user.getRole()));
	}
}
