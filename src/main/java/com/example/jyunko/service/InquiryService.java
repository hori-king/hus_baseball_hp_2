package com.example.jyunko.service;

import java.time.LocalDateTime;

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
}
