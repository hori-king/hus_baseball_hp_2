package com.example.jyunko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jyunko.service.MatchService;
import com.example.jyunko.service.NewsService;

@Controller
public class HomeController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private MatchService matchService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("latestNews", newsService.findTop3());
		model.addAttribute("latestMatches", matchService.findTop3());
		return "index";
	}

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
}
