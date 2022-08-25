package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.CategoryDao;
import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.CategoryItem;
import kr.or.connect.reservation.dto.ProductItem;
import kr.or.connect.reservation.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductDao productDao;
	private final CategoryDao categoryDao;
	
	@Autowired
	public ProductServiceImpl(ProductDao productDao, CategoryDao categoryDao) {
		this.productDao = productDao;
		this.categoryDao = categoryDao;
	}
	
	@Override
	public List<ProductItem> getProducts(Integer categoryId, Integer start) {
		return productDao.selectProducts(categoryId, start);
	}

	@Override
	public int getProductCntById(Integer categoryId) {
		
		List<CategoryItem> selectAll = categoryDao.selectAll();
		int productCnt = 0;

		if(categoryId == null || categoryId == 0) {
			for (CategoryItem categoryItem : selectAll) {
				productCnt += categoryItem.getCount();
			}
		} else {
			for (CategoryItem categoryItem : selectAll) {
				if(categoryId.equals(categoryItem.getId())) {
					productCnt += categoryItem.getCount();
					break;
				}
			}
		}

		return productCnt;
	}

}
