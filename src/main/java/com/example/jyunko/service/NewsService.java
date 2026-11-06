package com.example.jyunko.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jyunko.entity.News;
import com.example.jyunko.repository.NewsRepository;

@Service
public class NewsService {
	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private PhotoStorageService photoStorage;

	//全件取得
	public List<News> findAll() {
		return newsRepository.findByOrderByPostedDateDesc();
	}

	public List<News> findByCategory(String category) {
		return newsRepository.findByCategory(category);
	}

	//ID検索
	public News findById(Integer id) {
		return newsRepository.findById(id).orElse(null);
	}

	//最新3件取得
	public List<News> findTop3() {
		return newsRepository.findTop3ByOrderByPostedDateDesc();
	}

	//保存
	public void saveWithPhoto(News news, MultipartFile photo) throws IOException {
		// 投稿日が設定されていない場合、現在の日付を設定
		if (news.getPostedDate() == null) {
			news.setPostedDate(LocalDate.now());
		}

		if (!photo.isEmpty()) {
			news.setPhoto(photoStorage.save(photo));
		} else if (news.getId() != null) {
			// 更新時に新しいファイルがアップロードされなかった場合、古いパスを維持
			News existingNews = findById(news.getId());
			news.setPhoto(existingNews.getPhoto());
		}

		// ニュースを保存
		newsRepository.save(news);
	}

	//削除
	public void deleteById(Integer id) {
		// IDでニュースを削除
		newsRepository.deleteById(id);
	}
}
