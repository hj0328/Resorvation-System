package kr.or.connect.reservation.domain.promotion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
@RequiredArgsConstructor
public class PromotionController {
	
	private final PromotionService promotionService;
	
	@GetMapping(path = "/promotions")
	public  Map<String, Object> getPromotions() {
		List<PromotionItemDto> promotion = promotionService.getPromotions();
		Map<String, Object> map = new HashMap<>();
		map.put("items", promotion);
		return map;
	}
}
