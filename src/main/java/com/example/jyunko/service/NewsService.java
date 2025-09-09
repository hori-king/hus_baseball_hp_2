package com.example.jyunko.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jyunko.entity.News;
import com.example.jyunko.repository.NewsRepository;

@Service
public class NewsService {
	@Autowired
	private NewsRepository newsRepository;

	public List<News> findAll() {
		return newsRepository.findAll();
	}

	public News findById(Integer id) {
		return newsRepository.findById(id).orElse(null);
	}

	public List<News> findTop3() {
		return newsRepository.findTop3ByOrderByPostedDateDesc();
	}
}
