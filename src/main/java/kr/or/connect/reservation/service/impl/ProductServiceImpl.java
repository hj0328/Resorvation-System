package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.ProductItem;
import kr.or.connect.reservation.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductDao productDao;
	
	@Autowired
	public ProductServiceImpl(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	@Override
	public List<ProductItem> getProducts(int categoryId, int start) {
		return productDao.selectProducts(categoryId, start);
	}

	@Override
	public int getProductCntById(int categoryId) {
		return productDao.selectProductCntById(categoryId);
	}

}
