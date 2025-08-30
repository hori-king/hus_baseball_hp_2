package com.example.jyunko.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jyunko.entity.Match;
import com.example.jyunko.repository.MatchRepository;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;

	public List<Match> findAll() {
		return matchRepository.findAll();
	}
}
