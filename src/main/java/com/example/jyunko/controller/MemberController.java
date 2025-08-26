package com.example.jyunko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jyunko.entity.Member;
import com.example.jyunko.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/members")
	public String getMembers(Model model) {
		List<Member> members = memberService.findAll();
		model.addAttribute("members", members);
		return "members";
	}

}
