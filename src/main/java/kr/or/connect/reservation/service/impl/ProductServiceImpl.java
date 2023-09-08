package kr.or.connect.reservation.service.impl;

import java.util.*;

import kr.or.connect.reservation.utils.UtilConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.CommentDto;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImageDto;
import kr.or.connect.reservation.dto.ProductImageDto;
import kr.or.connect.reservation.dto.ProductItemDto;
import kr.or.connect.reservation.dto.ProductPriceDto;
import kr.or.connect.reservation.service.CommentService;
import kr.or.connect.reservation.service.ProductService;
import org.springframework.util.StringUtils;

import javax.swing.*;

import static kr.or.connect.reservation.utils.UtilConstant.ALL_PRODUCTS;

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
		if (ALL_PRODUCTS.equals(categoryId)) {
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
	public Map<String, Object> getProductDetail(int displayInfoId) {
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
