package kr.or.connect.reservation.service;

import java.util.List;
import java.util.Map;

import kr.or.connect.reservation.dto.ProductItemDto;

public interface ProductService {
	List<ProductItemDto> getProducts(Integer categoryId, Integer start);
	int getProductTotalCountById(int categoryId);
	Map<String, Object> getProductDetail(Integer displayInfoId);

}
