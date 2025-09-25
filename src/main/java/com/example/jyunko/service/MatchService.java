package com.example.jyunko.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jyunko.entity.Match;
import com.example.jyunko.repository.MatchRepository;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;

	//全件取得
	public List<Match> findAll() {
		return matchRepository.findByOrderByMatchDateDesc();
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
	public void saveWithPhoto(Match match, MultipartFile photo) throws IOException {
		// 写真がアップロードされているかチェック
		if (!photo.isEmpty()) {
			// アップロード先のディレクトリを指定
			String uploadDir = "src/main/resources/static/images/matches/";

			// ファイル名が重複しないように、ファイル名を生成
			String originalFileName = photo.getOriginalFilename();
			// 拡張子を取得
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			// 新しいファイル名を生成
			String newFileName = UUID.randomUUID().toString() + extension;

			// ファイルをサーバーに保存
			Path filePath = Paths.get(uploadDir + newFileName);
			Files.copy(photo.getInputStream(), filePath);

			// 試合のphotoフィールドに保存したファイルのパスを設定
			match.setPhoto("/images/matches/" + newFileName);
		} else if (match.getId() != null) {
			//更新時、新しいファイルがアップロードされなかった場合、古いパスを維持
			Match existingMatch = findById(match.getId());
			match.setPhoto(existingMatch.getPhoto());
		}

		// 試合を保存
		matchRepository.save(match);
	}

	//削除
	public void deleteById(Integer id) {
		// IDで試合を削除
		matchRepository.deleteById(id);
	}

}
