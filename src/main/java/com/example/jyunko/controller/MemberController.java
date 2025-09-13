package com.example.jyunko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jyunko.entity.Member;
import com.example.jyunko.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/members")
	public String getMembers(Model model) {
		List<Member> memberList = memberService.findAll();
		model.addAttribute("memberList", memberList);
		return "members";
	}

	@GetMapping("/admin/members")
	public String showMembers(Model model) {
		List<Member> memberList = memberService.findAll();
		model.addAttribute("memberList", memberList);
		return "admin/members/list";
	}

	@GetMapping("/admin/members/new")
	public String memberForm(Model model) {
		model.addAttribute("member", new Member());
		return "admin/members/form";
	}

	@PostMapping("/admin/members")
	public String createMember(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
		member.setId(null);
		memberService.save(member);
		redirectAttributes.addFlashAttribute("successMessage", "メンバーを登録しました。");
		return "redirect:/admin/members";
	}

	@GetMapping("/admin/members/{id}/edit")
	public String editMemberForm(@PathVariable Integer id, Model model) {
		Member member = memberService.findById(id);
		model.addAttribute("member", member);
		return "admin/members/form";
	}

	@PostMapping("/admin/members/{id}/edit")
	public String editMember(@PathVariable Integer id,
			@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
		member.setId(id);
		memberService.save(member);
		redirectAttributes.addFlashAttribute("successMessage", "メンバー情報を更新しました。");
		return "redirect:/admin/members";

	}

	@PostMapping("/admin/members/{id}/delete")
	public String deleteMember(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		memberService.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "メンバーを削除しました。");
		return "redirect:/admin/members";
	}

}
