package com.example.jyunko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jyunko.entity.Inquiry;
import com.example.jyunko.service.InquiryService;

@Controller
public class InquiryController {
	@Autowired
	private InquiryService inquiryService;

	// ユーザー向け問い合わせフォーム表示
	@GetMapping("/inquiry")
	public String showInquiryForm(Model model) {
		model.addAttribute("inquiry", new Inquiry());
		return "inquiry";
	}

	// ユーザー向け問い合わせフォーム送信処理
	@PostMapping("/inquiry")
	public String saveInquiry(@ModelAttribute Inquiry inquiry, RedirectAttributes redirectAttributes) {
		// ステータスを未対応に設定
		inquiryService.save(inquiry);
		//送信完了したらメッセージを返す
		redirectAttributes.addFlashAttribute("successMessage", "送信完了しました。");
		return "redirect:/inquiry";
	}

	// 管理者向け問い合わせ一覧表示
	@GetMapping("/admin/inquiries")
	public String ListInquiries(Model model) {
		List<Inquiry> inquiryList = inquiryService.findAll();
		model.addAttribute("inquiryList", inquiryList);
		return "admin/inquiries/list";
	}

	// 管理者向け問い合わせ詳細表示
	@GetMapping("/admin/inquiries/{id}")
	public String showInquiryDetail(@PathVariable Integer id, Model model) {
		// IDで問い合わせを検索
		Inquiry inquiry = inquiryService.findById(id);
		model.addAttribute("inquiry", inquiry);
		return "admin/inquiries/inquiry_detail";
	}

	// 管理者向け問い合わせステータス更新処理
	@PostMapping("/admin/inquiries/{id}/update-status")
	public String updateInquiryStatus(@PathVariable Integer id, @RequestParam Integer status) {
		// ステータスを更新
		inquiryService.updateStatus(id, status);
		return "redirect:/admin/inquiries";
	}

	// 管理者向け問い合わせ削除処理
	@PostMapping("/admin/inquiries/{id}/delete")
	public String deleteInquiry(@PathVariable Integer id) {
		// 問い合わせを削除
		inquiryService.deleteById(id);
		return "redirect:/admin/inquiries";
	}
}
