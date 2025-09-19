package com.example.jyunko.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jyunko.entity.Member;
import com.example.jyunko.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	//全件取得
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	//ID検索
	public Member findById(Integer id) {
		return memberRepository.findById(id).orElse(null);
	}

	//保存
	public void save(Member member) {
		// メンバーを保存
		memberRepository.save(member);
	}

	//削除
	public void deleteById(Integer id) {
		// IDでメンバーを削除
		memberRepository.deleteById(id);
	}
}
