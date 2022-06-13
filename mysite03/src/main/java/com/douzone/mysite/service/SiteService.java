package com.douzone.mysite.service;

import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.Autowired;
import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.repository.SiteVo;

@Service
public class SiteService {
	
	@Autowired
	private SiteRepository siteRepository;
	
	public SiteVo getSite() {
		return siteRepository.find();
	}

}
