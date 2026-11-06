package com.example.jyunko.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jyunko.entity.Member;
import com.example.jyunko.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PhotoStorageService photoStorage;

	//全件取得
	public List<Member> findAll() {
		return memberRepository.findByOrderByGradeDescIdAsc();
	}

	//ID検索
	public Member findById(Integer id) {
		return memberRepository.findById(id).orElse(null);
	}

	//保存
	public void saveWithPhoto(Member member, MultipartFile photo) throws IOException {
		// 写真がアップロードされているかチェック
		if (!photo.isEmpty()) {
			member.setPhoto(photoStorage.save(photo));
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
