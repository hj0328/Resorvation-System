package kr.or.connect.reservation.service;

import java.util.List;
import java.util.Map;

import kr.or.connect.reservation.dto.ProductItemDto;

public interface ProductService {
	List<ProductItemDto> getProducts(int categoryId, int start);
	int getProductCountById(int categoryId);
	Map<String, Object> getDisplayInfo(int displayInfoId);

}
