package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.PromotionItemDto;

public interface PromotionService {
	List<PromotionItemDto> getPromotions();
}
