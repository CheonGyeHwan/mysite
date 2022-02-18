package com.poscoict.mysite.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.service.FileUploadService;
import com.poscoict.mysite.service.GalleryService;
import com.poscoict.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.getImages();
		model.addAttribute("list", list);
		
		return "gallery/index";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		galleryService.removeImage(no);
		return "redirect:/gallery";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(GalleryVo galleryVo, @RequestParam("file") MultipartFile multipartFile) {
		String url = fileUploadService.restore(multipartFile);
		
		if (url != null) {
			galleryVo.setUrl(url);
			galleryService.saveImage(galleryVo);
		}
		
		return "redirect:/gallery";
	}
	
}
