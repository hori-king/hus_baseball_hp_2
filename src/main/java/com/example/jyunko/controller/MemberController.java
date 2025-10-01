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

import com.example.jyunko.entity.Member;
import com.example.jyunko.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	//photoフィールドのバインドを無効化
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("photo");
	}

	//部員一覧を表示
	@GetMapping("/members")
	public String getMembers(Model model) {
		//全ての部員情報をデータベースから取得
		List<Member> memberList = memberService.findAll();
		//モデルにセット
		model.addAttribute("memberList", memberList);
		
		//学年の選択肢をモデルにセット
	    List<Integer> grades = List.of(6,5,4,3,2,1);
	    model.addAttribute("grades", grades);
		
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
		//ポジションの選択肢をモデルにセット
		model.addAttribute("positions", List.of("投手", "捕手", "内野手", "外野手", "マネージャー"));
		//学部の選択肢をモデルにセット
		model.addAttribute("faculties", List.of("工学部", "情報科学部", "薬学部", "保健医療学部", "未来デザイン学部"));
		//学科の選択肢をモデルにセット
		model.addAttribute("departments", List.of("機械工学科", "情報工学科", "電気電子工学科", "建築学科", "都市環境学科",
				"情報科学科", "薬学科", "看護学科", "理学療法学科", "臨床工学科", "診療放射線学科",
				"メディアデザイン学科", "人間社会学科"));
		//空のMemberオブジェクトをモデルにセット
		model.addAttribute("member", new Member());
		//新規登録フォームを表示
		return "admin/members/form";
	}

	//新規部員をデータベースに保存
	@PostMapping("/admin/members")
	public String createMember(@ModelAttribute @Valid Member member, BindingResult bindingResult,
			@RequestParam("photo") MultipartFile photo,
			RedirectAttributes redirectAttributes, Model model) {
		//写真がアップロードされているかチェック
		if (photo.isEmpty()) {
			bindingResult.rejectValue("photo", "error.members", "写真をアップロードしてください。");
		}

		//エラーチェック
		if (bindingResult.hasErrors()) {
			//ポジションの選択肢をモデルにセット
			model.addAttribute("positions", List.of("投手", "捕手", "内野手", "外野手", "マネージャー"));
			//学部の選択肢をモデルにセット
			model.addAttribute("faculties", List.of("工学部", "情報科学部", "薬学部", "保健医療学部", "未来デザイン学部"));
			//学科の選択肢をモデルにセット
			model.addAttribute("departments", List.of("機械工学科", "情報工学科", "電気電子工学科", "建築学科", "都市環境学科",
					"情報科学科", "薬学科", "看護学科", "理学療法学科", "臨床工学科", "診療放射線学科",
					"メディアデザイン学科", "人間社会学科"));
			//フォームに戻る
			return "admin/members/form";
		}

		try {
			//写真を含めて保存
			memberService.saveWithPhoto(member, photo);
		} catch (IOException e) {
			//ポジションの選択肢をモデルにセット
			model.addAttribute("positions", List.of("投手", "捕手", "内野手", "外野手", "マネージャー"));
			//学部の選択肢をモデルにセット
			model.addAttribute("faculties", List.of("工学部", "情報科学部", "薬学部", "保健医療学部", "未来デザイン学部"));
			//学科の選択肢をモデルにセット
			model.addAttribute("departments", List.of("機械工学科", "情報工学科", "電気電子工学科", "建築学科", "都市環境学科",
					"情報科学科", "薬学科", "看護学科", "理学療法学科", "臨床工学科", "診療放射線学科",
					"メディアデザイン学科", "人間社会学科"));
			bindingResult.rejectValue("photo", "error.members", "写真のアップロードに失敗しました。");
			return "admin/members/form";
		}

		//リダイレクト時に一度だけ表示するメッセージ
		redirectAttributes.addFlashAttribute("successMessage", "メンバーを登録しました。");
		//部員一覧画面にリダイレクト
		return "redirect:/admin/members";
	}

	//編集フォームを表示
	@GetMapping("/admin/members/{id}/edit")
	public String editMemberForm(@PathVariable Integer id, Model model) {
		//ポジションの選択肢をモデルにセット
		model.addAttribute("positions", List.of("投手", "捕手", "内野手", "外野手", "マネージャー"));
		//学部の選択肢をモデルにセット
		model.addAttribute("faculties", List.of("工学部", "情報科学部", "薬学部", "保健医療学部", "未来デザイン学部"));
		//学科の選択肢をモデルにセット
		model.addAttribute("departments", List.of("機械工学科", "情報工学科", "電気電子工学科", "建築学科", "都市環境学科",
				"情報科学科", "薬学科", "看護学科", "理学療法学科", "臨床工学科", "診療放射線学科",
				"メディアデザイン学科", "人間社会学科"));
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
			@ModelAttribute @Valid Member member, BindingResult bindingResult,
			@RequestParam("photo") MultipartFile photo,
			RedirectAttributes redirectAttributes, Model model) {
		//エラーチェック
		if (bindingResult.hasErrors()) {
			//ポジションの選択肢をモデルにセット
			model.addAttribute("positions", List.of("投手", "捕手", "内野手", "外野手", "マネージャー"));
			//学部の選択肢をモデルにセット
			model.addAttribute("faculties", List.of("工学部", "情報科学部", "薬学部", "保健医療学部", "未来デザイン学部"));
			//学科の選択肢をモデルにセット
			model.addAttribute("departments", List.of("機械工学科", "情報工学科", "電気電子工学科", "建築学科", "都市環境学科",
					"情報科学科", "薬学科", "看護学科", "理学療法学科", "臨床工学科", "診療放射線学科",
					"メディアデザイン学科", "人間社会学科"));
			//フォームに戻る
			return "admin/members/form";
		}

		//idをセットしてデータベースに保存
		member.setId(id);

		try {
			//写真を含めて保存
			memberService.saveWithPhoto(member, photo);
		} catch (IOException e) {
			//ポジションの選択肢をモデルにセット
			model.addAttribute("positions", List.of("投手", "捕手", "内野手", "外野手", "マネージャー"));
			//学部の選択肢をモデルにセット
			model.addAttribute("faculties", List.of("工学部", "情報科学部", "薬学部", "保健医療学部", "未来デザイン学部"));
			//学科の選択肢をモデルにセット
			model.addAttribute("departments", List.of("機械工学科", "情報工学科", "電気電子工学科", "建築学科", "都市環境学科",
					"情報科学科", "薬学科", "看護学科", "理学療法学科", "臨床工学科", "診療放射線学科",
					"メディアデザイン学科", "人間社会学科"));
			//リダイレクト時に一度だけ表示するメッセージ
			redirectAttributes.addFlashAttribute("errorMessage", "写真のアップロードに失敗しました。");
			return "redirect:/admin/members/form";
		}

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
