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

import com.example.jyunko.entity.Match;
import com.example.jyunko.service.MatchService;

@Controller
public class MatchController {
	@Autowired
	private MatchService matchService;

	@GetMapping("/matches")
	public String getAllMatches(Model model) {
		List<Match> matchList = matchService.findAll();
		model.addAttribute("matchList", matchList);
		return "matches";
	}

	@GetMapping("/matches/{id}")
	public String showMatchDetail(@PathVariable Integer id, Model model) {
		Match match = matchService.findById(id);
		model.addAttribute("match", match);
		return "match_detail";
	}

	@GetMapping("/admin/matches")
	public String showMatches(Model model) {
		List<Match> matchList = matchService.findAll();
		model.addAttribute("matchList", matchList);
		return "admin/matches/list";
	}

	@GetMapping("/admin/matches/new")
	public String matchForm(Model model) {
		model.addAttribute("match", new Match());
		return "admin/matches/form";
	}

	@PostMapping("/admin/matches")
	public String createMatch(@ModelAttribute @Valid Match match, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "エラーが発生しました。");
			return "admin/matches/form";
		}
		matchService.save(match);
		redirectAttributes.addFlashAttribute("successMessage", "試合結果を登録しました。");
		return "redirect:/admin/matches";
	}

}
