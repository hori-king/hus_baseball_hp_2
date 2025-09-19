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

	//試合結果一覧を表示
	@GetMapping("/matches")
	public String getAllMatches(Model model) {
		//全ての試合結果をデータベースから取得
		List<Match> matchList = matchService.findAll();
		model.addAttribute("matchList", matchList);
		return "matches";
	}

	//試合結果詳細を表示
	@GetMapping("/matches/{id}")
	public String showMatchDetail(@PathVariable Integer id, Model model) {
		//IDに基づいて試合結果をデータベースから取得
		Match match = matchService.findById(id);
		model.addAttribute("match", match);
		return "match_detail";
	}

	//管理者用の試合結果一覧を表示
	@GetMapping("/admin/matches")
	public String showMatches(Model model) {
		//全ての試合結果をデータベースから取得
		List<Match> matchList = matchService.findAll();
		model.addAttribute("matchList", matchList);
		return "admin/matches/list";
	}

	//新規登録フォームを表示
	@GetMapping("/admin/matches/new")
	public String matchForm(Model model) {
		//空のMatchオブジェクトをモデルにセット
		model.addAttribute("match", new Match());
		return "admin/matches/form";
	}

	//新規試合結果をデータベースに保存
	@PostMapping("/admin/matches")
	public String createMatch(@ModelAttribute @Valid Match match, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		//エラーチェック
		if (bindingResult.hasErrors()) {
			//リダイレクト時に一度だけ表示するメッセージ
			redirectAttributes.addFlashAttribute("errorMessage", "エラーが発生しました。");
			return "admin/matches/form";
		}
		//データベースに保存
		matchService.save(match);
		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "試合結果を登録しました。");
		return "redirect:/admin/matches";
	}

	//編集フォームを表示
	@GetMapping("/admin/matches/{id}/edit")
	public String editMatchForm(@PathVariable Integer id, Model model) {
		//idに該当する試合結果をデータベースから取得
		Match match = matchService.findById(id);
		//モデルにセット
		model.addAttribute("match", match);
		return "admin/matches/form";
	}

	//試合結果を更新
	@PostMapping("/admin/matches/{id}/edit")
	public String updateMatch(@PathVariable Integer id, @ModelAttribute @Valid Match match, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		//エラーチェック
		if (bindingResult.hasErrors()) {
			//リダイレクト時に一度だけ表示するメッセージ
			redirectAttributes.addFlashAttribute("errorMessage", "エラーが発生しました。");
			return "admin/matches";
		}
		//IDを設定してデータベースに保存
		match.setId(id);
		matchService.save(match);
		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "試合結果を更新しました。");
		return "redirect:/admin/matches";
	}

	//試合結果を削除
	@PostMapping("/admin/matches/{id}/delete")
	public String deleteMatch(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		//IDに基づいて試合結果をデータベースから削除
		matchService.deleteById(id);
		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "試合結果を削除しました。");
		return "redirect:/admin/matches";
	}

}
