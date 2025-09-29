package com.example.jyunko.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.Match;

@Repository
// Matchエンティティに対するCRUD操作を提供するリポジトリインターフェース
public interface MatchRepository extends JpaRepository<Match, Integer> {
	// 最新の試合を3件取得するメソッド
	List<Match> findTop3ByOrderByMatchDateDesc();

	// 試合日の降順で全ての試合を取得するメソッド
	List<Match> findByOrderByMatchDateDesc();

	// 試合日から重複しない年を取得するカスタムクエリ
	@Query("SELECT DISTINCT YEAR(m.matchDate) FROM Match m ORDER BY YEAR(m.matchDate) DESC")
	List<Integer> findDistinctYears();

	// 指定された日付範囲内の試合を試合日の降順で取得するメソッド
	List<Match> findByMatchDateBetweenOrderByMatchDateDesc(LocalDate startDate, LocalDate endDate);

}
