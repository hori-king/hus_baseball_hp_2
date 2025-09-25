package com.example.jyunko.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jyunko.entity.News;
import com.example.jyunko.repository.NewsRepository;

@Service
public class NewsService {
	@Autowired
	private NewsRepository newsRepository;

	//全件取得
	public List<News> findAll() {
		return newsRepository.findByOrderByPostedDateDesc();
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
			// アップロード先のディレクトリ
			String uploadDir = "src/main/resources/static/images/news/";
			// 元のファイル名を取得
			String originalFileName = photo.getOriginalFilename();
			// 拡張子を取得
			String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
			// 新しいファイル名を生成
			String newFileName = UUID.randomUUID().toString() + extention;

			// サーバにファイルを保存
			Path filePath = Paths.get(uploadDir + newFileName);
			Files.copy(photo.getInputStream(), filePath);

			// Newsエンティティのphotoフィールドに保存したファイルのパスを設定
			news.setPhoto("/images/news/" + newFileName);
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
