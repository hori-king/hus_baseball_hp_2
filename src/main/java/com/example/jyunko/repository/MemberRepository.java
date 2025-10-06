package com.example.jyunko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.Member;

@Repository
// Memberエンティティに対するCRUD操作を提供するリポジトリインターフェース
public interface MemberRepository extends JpaRepository<Member, Integer> {
	// 学年順にメンバーを取得するメソッド
	List<Member> findByOrderByGradeDescIdAsc();
}
