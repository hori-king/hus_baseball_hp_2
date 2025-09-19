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

	//全件取得
	public List<Match> findAll() {
		return matchRepository.findAll();
	}

	//ID検索
	public Match findById(Integer id) {
		return matchRepository.findById(id).orElse(null);
	}

	//最新3件取得
	public List<Match> findTop3() {
		return matchRepository.findTop3ByOrderByMatchDateDesc();
	}

	//保存
	public void save(Match match) {
		// 試合を保存
		matchRepository.save(match);
	}

	//削除
	public void deleteById(Integer id) {
		// IDで試合を削除
		matchRepository.deleteById(id);
	}

}
