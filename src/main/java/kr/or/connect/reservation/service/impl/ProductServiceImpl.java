package kr.or.connect.reservation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.ProductDao;
import kr.or.connect.reservation.dto.CommentDto;
import kr.or.connect.reservation.dto.CommentImageDto;
import kr.or.connect.reservation.dto.DisplayInfoDto;
import kr.or.connect.reservation.dto.DisplayInfoImageDto;
import kr.or.connect.reservation.dto.ProductImageDto;
import kr.or.connect.reservation.dto.ProductItemDto;
import kr.or.connect.reservation.dto.ProductPriceDto;
import kr.or.connect.reservation.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductDao productDao;

	@Autowired
	public ProductServiceImpl(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public List<ProductItemDto> getProducts(int categoryId, int start) {
		List<ProductItemDto> products = null;
		if(categoryId == 0) {
			products = productDao.selectAllProducts(start);
		} else {
			products = productDao.selectProducts(categoryId, start);
		}

		return products;
	}

	@Override
	public int getProductCntById(int categoryId) {
		return productDao.selectProductCntById(categoryId);
	}

	@Override
	public Map<String, Object> getDisplayInfo(int displayInfoId) {
		Map<String, Object> displayInfoMap = new HashMap<>();

		DisplayInfoDto displayInfo = productDao.selectDisplayInfo(displayInfoId);
		displayInfoMap.put("displayInfo", displayInfo);

		List<ProductImageDto> productImageList = productDao.selectProductImage(displayInfoId);
		displayInfoMap.put("productImages", productImageList);

		DisplayInfoImageDto displayInfoImage = productDao.selectDisplayInfoImage(displayInfoId);
		displayInfoMap.put("displayInfoImage", displayInfoImage);

		List<CommentDto> comments = getComments(displayInfoId);
		displayInfoMap.put("comments", comments);

		Double avgScore = productDao.selectAvgScore(displayInfoId);
		displayInfoMap.put("averageScore", avgScore);

		return displayInfoMap;
	}

	private List<CommentDto> getComments(int displayInfoId) {
		List<CommentDto> commentList = productDao.selectComment(displayInfoId);
		for (CommentDto commentDto : commentList) {
			List<CommentImageDto> commentImageList = productDao.selectCommentImageByCommentId(commentDto.getCommentId());
			commentDto.setCommentImages(commentImageList);
		}
		return commentList;
	}
}
