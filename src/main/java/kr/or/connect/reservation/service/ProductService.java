package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.ProductItem;

public interface ProductService {
	List<ProductItem> getProducts(int categoryId, int start);
	int getProductCntById(int categoryId);
}
