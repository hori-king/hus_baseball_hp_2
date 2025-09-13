package com.example.jyunko.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	//部員一覧を表示
	@GetMapping("/members")
	public String getMembers(Model model) {
		//全ての部員情報をデータベースから取得
		List<Member> memberList = memberService.findAll();
		//モデルにセット
		model.addAttribute("memberList", memberList);
		//部員一覧画面を表示
		return "members";
	}

	//管理者用の部員一覧を表示
	@GetMapping("/admin/members")
	public String showMembers(Model model) {
		//全ての部員情報をデータベースから取得
		List<Member> memberList = memberService.findAll();
		//モデルにセット
		model.addAttribute("memberList", memberList);
		//一覧画面を表示
		return "admin/members/list";
	}

	//新規登録フォームを表示
	@GetMapping("/admin/members/new")
	public String memberForm(Model model) {
		//空のMemberオブジェクトをモデルにセット
		model.addAttribute("member", new Member());
		//新規登録フォームを表示
		return "admin/members/form";
	}

	//新規部員をデータベースに保存
	@PostMapping("/admin/members")
	public String createMember(@ModelAttribute @Valid Member member, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		//エラーチェック
		if (bindingResult.hasErrors()) {
			//フォームに戻る
			return "admin/members/form";
		}
		//データベースに保存
		memberService.save(member);
		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "メンバーを登録しました。");
		//部員一覧画面にリダイレクト
		return "redirect:/admin/members";
	}

	//編集フォームを表示
	@GetMapping("/admin/members/{id}/edit")
	public String editMemberForm(@PathVariable Integer id, Model model) {
		//idに該当する部員情報をデータベースから取得
		Member member = memberService.findById(id);
		//モデルにセット
		model.addAttribute("member", member);
		//編集フォームを表示
		return "admin/members/form";
	}

	//部員情報の更新
	@PostMapping("/admin/members/{id}/edit")
	public String editMember(@PathVariable Integer id,
			@ModelAttribute @Valid Member member, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		//エラーチェック
		if (bindingResult.hasErrors()) {
			//フォームに戻る
			return "admin/members/form";
		}
		//idをセットしてデータベースに保存
		member.setId(id);
		memberService.save(member);
		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "メンバー情報を更新しました。");
		//部員一覧画面にリダイレクト
		return "redirect:/admin/members";

	}

	//部員の削除
	@PostMapping("/admin/members/{id}/delete")
	public String deleteMember(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		//idに該当する部員情報をデータベースから削除
		memberService.deleteById(id);
		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "メンバーを削除しました。");
		//部員一覧画面にリダイレクト
		return "redirect:/admin/members";
	}

}
