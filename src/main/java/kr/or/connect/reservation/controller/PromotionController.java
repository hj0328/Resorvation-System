package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.PromotionItemDto;
import kr.or.connect.reservation.service.PromotionService;

@RestController
@RequestMapping(path="/api")
public class PromotionController {
	
	private final PromotionService promotionService;
	
	public PromotionController(PromotionService promotionService) {
		this.promotionService = promotionService;
	}
	
	@GetMapping(path = "/promotions")
	public  Map<String, Object> getPromotions() {
		List<PromotionItemDto> promotion = promotionService.getPromotions();
		Map<String, Object> map = new HashMap<>();
		map.put("items", promotion);
		return map;
	}
}
