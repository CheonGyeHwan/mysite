package com.poscoict.mysite.controller.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@RestController("GuestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping
	public ResponseEntity list(@RequestParam(value="sn", required=true, defaultValue="0") Long no) {
		List<GuestbookVo> list = guestbookService.getMessageList(no);
		
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(list));
	}
	
	@PostMapping
	public ResponseEntity add(@RequestBody GuestbookVo guestbookVo) {
		guestbookService.addMessage(guestbookVo);
		guestbookVo.setNo(1L);
		guestbookVo.setPassword("");
		
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(guestbookVo));
	}	
	
	@DeleteMapping("/{no}")
	public ResponseEntity delete(@PathVariable("no") Long no, @RequestParam(value="password", required=true, defaultValue="") String password) {
		boolean result = guestbookService.deleteMessage(no, password);
		
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(result));
	}
	
}
