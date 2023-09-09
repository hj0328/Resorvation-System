package kr.or.connect.reservation.service.impl;

import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.*;
import kr.or.connect.reservation.service.CommentService;
import kr.or.connect.reservation.service.ProductService;
import kr.or.connect.reservation.utils.UtilConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductDao productDao;

	private final CommentService commentService;

	@Autowired
	public ProductServiceImpl(ProductDao productDao, CommentService commentService) {
		this.productDao = productDao;
		this.commentService = commentService;
	}

	@Override
	public List<ProductItemDto> getProducts(Integer categoryId, Integer start) {
		List<ProductItemDto> products = Collections.emptyList();
		if (UtilConstant.ALL_PRODUCTS.equals(categoryId)) {
			products = productDao.selectAllProducts(start);
		} else {
			products = productDao.selectProducts(categoryId, start);
		}

		return products;
	}

	@Override
	public int getProductTotalCountById(int categoryId) {
		return productDao.selectProductCountById(categoryId);
	}

	@Override
	public Map<String, Object> getProductDetail(Integer displayInfoId) {
		Map<String, Object> displayInfoMap = new HashMap<>();

		DisplayInfo displayInfo = productDao.selectDisplayInfo(displayInfoId);
		displayInfoMap.put("displayInfo", displayInfo);

		List<ProductImageDto> productImageList = productDao.selectProductImage(displayInfoId);
		displayInfoMap.put("productImages", productImageList);

		DisplayInfoImageDto displayInfoImage = productDao.selectDisplayInfoImage(displayInfoId);
		displayInfoMap.put("displayInfoImage", displayInfoImage);

		List<ProductPriceDto> productPriceList = productDao.selectProductPrice(displayInfoId);
		displayInfoMap.put("productPriceId", productPriceList);

		List<CommentDto> comments = commentService.getComments(displayInfoId);
		displayInfoMap.put("comments", comments);

		Double averageScore = productDao.selectAverageScore(displayInfoId);
		displayInfoMap.put("averageScore", averageScore);

		return displayInfoMap;
	}
}
