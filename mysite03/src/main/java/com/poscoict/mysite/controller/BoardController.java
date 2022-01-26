package com.poscoict.mysite.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.security.AuthUser;
import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model) {
		Map<String, Object> map = boardService.getContentsList(1, null);
		
		model.addAttribute("map", map);	
		return "board/list";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String list(Model model, String keyword) {
		Map<String, Object> map = boardService.getContentsList(1, keyword);
		
		model.addAttribute("map", map);	
		return "board/list";
	}
	
	@RequestMapping("/{currentPage}")
	public String list(Model model, @PathVariable("currentPage") int currentPage) {
		Map<String, Object> map = boardService.getContentsList(currentPage, null);
		
		model.addAttribute("map", map);	
		return "board/list";
	}
	
	@RequestMapping(value="/{keyword}/{currentPage}", method=RequestMethod.GET)
	public String list(Model model, @PathVariable("keyword") String keyword, @PathVariable("currentPage") int currentPage) {
		Map<String, Object> map = boardService.getContentsList(currentPage, keyword);
		
		model.addAttribute("map", map);	
		return "board/list";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BoardVo boardVo, Model model) {
		boardService.addContents(boardVo);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/write/{no}", method=RequestMethod.GET)
	public String write(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		return "board/write";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		
		model.addAttribute("boardVo", boardVo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping("/modify/{no}")
	public String modify(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(BoardVo boardVo) {	
		boardService.updateContents(boardVo);
		return "redirect:/board/view/" + boardVo.getNo();
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no) {	
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board";
	}
	
}
