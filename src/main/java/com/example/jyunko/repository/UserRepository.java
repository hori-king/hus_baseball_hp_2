package com.example.jyunko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.User;

@Repository
// Userエンティティに対するCRUD操作を提供するリポジトリインターフェース
public interface UserRepository extends JpaRepository<User, Integer> {
	// ユーザー名でユーザーを検索するメソッド
	public User findByUsername(String name);
}
