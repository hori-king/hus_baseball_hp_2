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

import com.example.jyunko.entity.News;
import com.example.jyunko.service.NewsService;

@Controller
public class NewsController {

	private final HomeController homeController;
	@Autowired
	private NewsService newsService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("photo");
	}

	NewsController(HomeController homeController) {
		this.homeController = homeController;
	}

	//お知らせ一覧を表示
	@GetMapping("/news")
	public String newsList(Model model) {
		//全てのお知らせをデータベースから取得
		List<News> newsList = newsService.findAll();
		//モデルにセット
		model.addAttribute("newsList", newsList);
		return "news";
	}

	//お知らせ詳細を表示
	@GetMapping("/news/{id}")
	public String viewNews(@PathVariable Integer id, Model model) {
		//IDに基づいてお知らせをデータベースから取得
		News news = newsService.findById(id);
		//モデルにセット
		model.addAttribute("newsItem", news);
		return "news_detail";
	}

	//管理者用のお知らせ一覧を表示
	@GetMapping("/admin/news")
	public String adminNewsList(Model model) {
		//全てのお知らせをデータベースから取得
		List<News> newsList = newsService.findAll();
		//モデルにセット
		model.addAttribute("newsList", newsList);
		return "admin/news/list";
	}

	//新規登録フォームを表示
	@GetMapping("/admin/news/new")
	public String NewsForm(Model model) {
		//空のNewsオブジェクトをモデルにセット
		model.addAttribute("news", new News());
		return "admin/news/form";
	}

	//新規お知らせをデータベースに保存
	@PostMapping("/admin/news")
	public String createNews(@ModelAttribute @Valid News news, BindingResult bindingResult,
			@RequestParam("photo") MultipartFile photo,
			RedirectAttributes redirectAttributes) {
		//写真がアップロードされていない場合、エラーを追加
		if (photo.isEmpty()) {
			bindingResult.rejectValue("photo", "error.news", "写真をアップロードしてください。");
		}

		//エラーチェック
		if (bindingResult.hasErrors()) {
			return "admin/news/form";
		}

		try {
			//写真を含めて保存
			newsService.saveWithPhoto(news, photo);
		} catch (IOException e) {
			bindingResult.rejectValue("photo", "error.news", "写真のアップロードに失敗しました。");
			return "admin/news/form";
		}

		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "お知らせを登録しました。");
		return "redirect:/admin/news";
	}

	//編集フォームを表示
	@GetMapping("/admin/news/{id}/edit")
	public String editNewsForm(@PathVariable Integer id, Model model) {
		//idに該当するお知らせをデータベースから取得
		News news = newsService.findById(id);
		//モデルにセット
		model.addAttribute("news", news);
		return "admin/news/form";
	}

	//お知らせを更新
	@PostMapping("/admin/news/{id}/edit")
	public String updateNews(@PathVariable Integer id, @ModelAttribute News news,
			@RequestParam("photo") MultipartFile photo,
			@Valid BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		//エラーチェック
		if (bindingResult.hasErrors()) {
			//リダイレクト時に一度だけ表示するメッセージ
			redirectAttributes.addFlashAttribute("errorMessage", "エラーが発生しました。");
			return "redirect:/admin/news";
		}
		//IDをセット
		news.setId(id);

		try {
			//写真を含めて保存
			newsService.saveWithPhoto(news, photo);
		} catch (IOException e) {
			bindingResult.rejectValue("photo", "error.news", "写真のアップロードに失敗しました。");
			return "admin/news/form";
		}

		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "お知らせを更新しました。");

		return "redirect:/admin/news";
	}

	//お知らせを削除
	@PostMapping("/admin/news/{id}/delete")
	public String deleteNews(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		//IDに該当するお知らせをデータベースから削除
		newsService.deleteById(id);
		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "お知らせを削除しました。");
		return "redirect:/admin/news";
	}
}
