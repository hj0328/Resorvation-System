package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.ProductItem;

public interface ProductService {
	public List<ProductItem> getProducts(Integer categoryId, Integer start);

	public int getProductCntById(Integer categoryId);
}
