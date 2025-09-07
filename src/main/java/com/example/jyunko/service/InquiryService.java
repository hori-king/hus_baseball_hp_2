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

	public void save(Inquiry inquiry) {
		inquiry.setStatus(0);
		inquiry.setCreatedAt(LocalDateTime.now());
		inquiryRepository.save(inquiry);
	}

	public List<Inquiry> findAll() {
		return inquiryRepository.findAll();
	}

	public Inquiry findById(Integer id) {
		return inquiryRepository.findById(id).orElse(null);
	}

	public void updateStatus(Integer id, Integer status) {
		Inquiry inquiry = findById(id);
		if (inquiry != null) {
			inquiry.setStatus(status);
			inquiryRepository.save(inquiry);
		}
	}

	public void deleteById(Integer id) {
		inquiryRepository.deleteById(id);
	}

}
