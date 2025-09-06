package com.example.jyunko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jyunko.entity.Inquiry;
import com.example.jyunko.service.InquiryService;

@Controller
public class InquiryController {
	@Autowired
	private InquiryService inquiryService;

	@GetMapping("/inquiry")
	public String showInquiryForm(Model model) {
		model.addAttribute("inquiry", new Inquiry());
		return "inquiry";
	}

	@PostMapping("/inquiry")
	public String saveInquiry(@ModelAttribute Inquiry inquiry, RedirectAttributes redirectAttributes) {
		inquiryService.save(inquiry);
		redirectAttributes.addFlashAttribute("successMessage", "送信完了しました。");
		return "redirect:/inquiry";
	}

	@GetMapping("/admin/inquiry")
	public String ListInquiries(Model model) {
		List<Inquiry> inquiryList = inquiryService.findAll();
		model.addAttribute("inquiries", inquiryList);
		return "admin/inquiries/list";
	}
}
