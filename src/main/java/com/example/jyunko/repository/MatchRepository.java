package com.example.jyunko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.Match;

@Repository
// Matchエンティティに対するCRUD操作を提供するリポジトリインターフェース
public interface MatchRepository extends JpaRepository<Match, Integer> {
	// 最新の試合を3件取得するメソッド
	List<Match> findTop3ByOrderByMatchDateDesc();
}
