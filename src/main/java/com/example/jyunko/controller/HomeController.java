package com.example.jyunko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jyunko.service.NewsService;

@Controller
public class HomeController {
	@Autowired
	private NewsService newsService;

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
}
