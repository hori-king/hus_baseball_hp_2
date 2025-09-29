package com.example.jyunko.controller;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jyunko.entity.Match;
import com.example.jyunko.service.MatchService;

@Controller
public class MatchController {
	@Autowired
	private MatchService matchService;

	//photoフィールドのバインドを無効化
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("photo");
	}

	//試合結果一覧を表示
	@GetMapping("/matches")
	public String getAllMatches(@RequestParam(name = "year", required = false) Integer year, Model model) {
		//全ての試合結果をデータベースから取得
		List<Match> matchList;

		if (year != null) {
			//年が指定されている場合、その年の試合結果を取得
			matchList = matchService.findByYear(year);
		} else {
			//年が指定されていない場合、全ての試合結果を取得
			matchList = matchService.findAll();
		}

		//存在する年をすべてモデルに追加
		model.addAttribute("years", matchService.findDistinctYears());
		//試合結果リストをモデルに追加
		model.addAttribute("matchList", matchList);
		//選択された年をモデルに追加
		model.addAttribute("selectedYear", year);

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
		//試合結果の選択肢をモデルにセット
		model.addAttribute("results", List.of("勝利", "敗北", "引き分け"));
		return "admin/matches/form";
	}

	//新規試合結果をデータベースに保存
	@PostMapping("/admin/matches")
	public String createMatch(@ModelAttribute @Valid Match match, BindingResult bindingResult,
			@RequestParam("photo") MultipartFile photo,
			RedirectAttributes redirectAttributes, Model model) {
		//写真がアップロードされているかチェック
		if (photo.isEmpty()) {
			bindingResult.rejectValue("photo", "error.matches", "写真をアップロードしてください。");
		}

		//エラーチェック
		if (bindingResult.hasErrors()) {
			//試合結果の選択肢をモデルにセット
			model.addAttribute("results", List.of("勝利", "敗北", "引き分け"));

			return "admin/matches/form";
		}

		try {
			matchService.saveWithPhoto(match, photo);
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "写真のアップロードに失敗しました。");
			return "admin/matches/form";
		}

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
		//試合結果の選択肢をモデルにセット
		model.addAttribute("results", List.of("勝利", "敗北", "引き分け"));
		return "admin/matches/form";
	}

	//試合結果を更新
	@PostMapping("/admin/matches/{id}/edit")
	public String updateMatch(@PathVariable Integer id,
			@ModelAttribute @Valid Match match, BindingResult bindingResult,
			@RequestParam("photo") MultipartFile photo,
			RedirectAttributes redirectAttributes, Model model) {
		//エラーチェック
		if (bindingResult.hasErrors()) {
			//リダイレクト時に一度だけ表示するメッセージ
			redirectAttributes.addFlashAttribute("errorMessage", "エラーが発生しました。");
			//試合結果の選択肢をモデルにセット
			model.addAttribute("results", List.of("勝利", "敗北", "引き分け"));
			return "admin/matches/form";
		}

		//IDを設定してデータベースに保存
		match.setId(id);

		try {
			//写真を含む試合結果を保存
			matchService.saveWithPhoto(match, photo);
		} catch (IOException e) {
			//写真のアップロードに失敗した場合、エラーメッセージを設定してフォームに戻る
			redirectAttributes.addFlashAttribute("errorMessage", "写真のアップロードに失敗しました。");
			return "admin/matches/form";
		}

		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "試合結果を更新しました。");
		//試合結果一覧画面にリダイレクト
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
