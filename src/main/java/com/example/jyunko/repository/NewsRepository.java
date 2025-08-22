package com.example.jyunko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

}
