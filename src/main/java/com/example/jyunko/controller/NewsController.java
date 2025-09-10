package com.example.jyunko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.jyunko.entity.News;
import com.example.jyunko.service.NewsService;

@Controller
public class NewsController {
	@Autowired
	private NewsService newsService;

	@GetMapping("/news")
	public String newsList(Model model) {
		List<News> newsList = newsService.findAll();
		model.addAttribute("newsList", newsList);
		return "news";
	}

	@GetMapping("/news/{id}")
	public String viewNews(@PathVariable Integer id, Model model) {
		News news = newsService.findById(id);
		model.addAttribute("newsItem", news);
		return "news_detail";
	}

	@GetMapping("/admin/news")
	public String adminNewsList(Model model) {
		List<News> newsList = newsService.findAll();
		model.addAttribute("newsList", newsList);
		return "admin/news/list";
	}

	@GetMapping("/admin/news/new")
	public String NewsForm(Model model) {
		model.addAttribute("news", new News());
		return "admin/news/form";
	}

}
