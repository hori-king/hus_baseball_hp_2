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

import com.example.jyunko.entity.Member;
import com.example.jyunko.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	//全件取得
	public List<Member> findAll() {
		return memberRepository.findByOrderByGradeDesc();
	}

	//ID検索
	public Member findById(Integer id) {
		return memberRepository.findById(id).orElse(null);
	}

	//保存
	public void saveWithPhoto(Member member, MultipartFile photo) throws IOException {
		// 写真がアップロードされているかチェック
		if (!photo.isEmpty()) {
			// 1. 保存先のベースとなるディレクトリを定義
			Path uploadDir = Paths.get("uploads/members");
			
		    // 2. ディレクトリが存在しない場合は、ここで自動的に作成する
			if (!Files.exists(uploadDir)) {
				Files.createDirectories(uploadDir);
			}

			// ファイル名が重複しないように、ファイル名を生成
			String originalFileName = photo.getOriginalFilename();
			// 拡張子を取得
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			// 新しいファイル名を生成
			String newFileName = UUID.randomUUID().toString() + extension;

			// ファイルをサーバーに保存
			Path filePath = uploadDir.resolve(newFileName);
			Files.copy(photo.getInputStream(), filePath);

			// メンバーのphotoフィールドに保存したファイルのパスを設定
			member.setPhoto("/uploads/members/" + newFileName);
		} else if (member.getId() != null) {
			//更新時、新しいファイルがアップロードされなかった場合、古いパスを維持
			Member existingMember = findById(member.getId());
			member.setPhoto(existingMember.getPhoto());
		}

		// メンバーを保存
		memberRepository.save(member);
	}

	//削除
	public void deleteById(Integer id) {
		// IDでメンバーを削除
		memberRepository.deleteById(id);
	}
}
