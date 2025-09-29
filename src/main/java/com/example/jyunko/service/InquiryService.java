package com.example.jyunko.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jyunko.entity.Inquiry;
import com.example.jyunko.repository.InquiryRepository;

@Service
public class InquiryService {
	@Autowired
	private InquiryRepository inquiryRepository;

	//保存
	public void save(Inquiry inquiry) {
		// 新規問い合わせのステータスを未対応(0)に設定
		inquiry.setStatus(0);
		// 作成日時を現在日時に設定
		inquiry.setCreatedAt(LocalDateTime.now());
		//新規問い合わせを保存
		inquiryRepository.save(inquiry);
	}

	//全件取得
	public List<Inquiry> findAll() {
		return inquiryRepository.findAll();
	}

	//ID検索
	public Inquiry findById(Integer id) {
		return inquiryRepository.findById(id).orElse(null);
	}

	//ステータス更新
	public void updateStatus(Integer id, Integer status) {
		// IDで問い合わせを検索
		Inquiry inquiry = findById(id);
		// 問い合わせが存在する場合、ステータスを更新して保存
		if (inquiry != null) {
			// ステータスを更新
			inquiry.setStatus(status);
			//更新内容を保存
			inquiryRepository.save(inquiry);
		}
	}

	//削除
	public void deleteById(Integer id) {
		// IDで問い合わせを削除
		inquiryRepository.deleteById(id);
	}

}
