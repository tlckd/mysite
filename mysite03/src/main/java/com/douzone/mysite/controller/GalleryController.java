package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("gallery")
public class GalleryController {

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("list",galleryService.getImages());
		return "gallery/index";
	}
	
	@RequestMapping("/upload")
	public String upload(GalleryVo vo, @RequestParam("file") MultipartFile multipartFile, Model model) {
		String url = fileUploadService.restore(multipartFile);
		model.addAttribute("url",url);
		
		vo.setUrl(url);
		
		galleryService.saveImage(vo);
		
		return "redirect:/gallery/";	
	}
	
	@RequestMapping("/delete/{no}")
	public String delete( @PathVariable("no") Long no ) {
		galleryService.removeImages(no);
		return "redirect:/gallery/";
	}
	
	
}
