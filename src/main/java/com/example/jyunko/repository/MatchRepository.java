package com.example.jyunko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jyunko.entity.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

}
