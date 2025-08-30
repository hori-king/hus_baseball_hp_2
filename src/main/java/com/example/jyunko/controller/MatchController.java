package com.example.jyunko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jyunko.entity.Match;
import com.example.jyunko.service.MatchService;

@Controller
public class MatchController {
	@Autowired
	private MatchService matchService;

	@GetMapping("/matches")
	public String getAllMatches(Model model) {
		List<Match> matches = matchService.findAll();
		model.addAttribute("matches", matches);
		return "matches";
	}
}
