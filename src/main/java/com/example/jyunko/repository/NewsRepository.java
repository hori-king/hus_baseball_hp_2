package com.example.jyunko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.News;

@Repository
// Newsエンティティに対するCRUD操作を提供するリポジトリインターフェース
public interface NewsRepository extends JpaRepository<News, Integer> {
	// 最新のニュースを3件取得するメソッド
	List<News> findTop3ByOrderByPostedDateDesc();
}
