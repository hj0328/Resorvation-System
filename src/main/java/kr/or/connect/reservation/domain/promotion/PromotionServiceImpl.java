package kr.or.connect.reservation.domain.promotion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

	private final PromotionDao promotionDao;

	@Override
	public List<PromotionItemDto> getPromotions() {
		return promotionDao.selectPromotions();
	}

}
