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

	// トップページ
	@GetMapping("/")
	public String index(Model model) {
		// 最新のニュース3件取得してモデルに追加
		model.addAttribute("latestNews", newsService.findTop3());
		// 最新の試合3件を取得してモデルに追加
		model.addAttribute("latestMatches", matchService.findTop3());
		return "index";
	}
	
	@GetMapping("/about")
	public String showAbout() {
		return "about";
	}

	// ログインページ
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	// 管理者ページ
	@GetMapping("/admin")
	public String showAdmin() {
		return "admin/index";
	}

}
