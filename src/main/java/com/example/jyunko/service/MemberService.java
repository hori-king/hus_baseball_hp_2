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

	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	public Member findById(Integer id) {
		return memberRepository.findById(id).orElse(null);
	}

	public void save(Member member) {
		memberRepository.save(member);
	}

	public void deleteById(Integer id) {
		memberRepository.deleteById(id);
	}
}
