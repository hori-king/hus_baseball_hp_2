package com.example.jyunko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.Inquiry;

@Repository
// Inquiryエンティティに対するCRUD操作を提供するリポジトリインターフェース
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

}
