package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.comment.CommentService;
import kr.or.connect.reservation.domain.comment.dto.CommentResponse;
import kr.or.connect.reservation.domain.product.dto.ProductItemDto;
import kr.or.connect.reservation.domain.product.dto.ProductPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.reservation.utils.UtilConstant.ALL_PRODUCTS;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final CommentService commentService;
	private final ProductDao productDao;

	public List<ProductItemDto> getProducts(Integer categoryId, Integer start) {
		List<ProductItemDto> products = Collections.emptyList();
		if (ALL_PRODUCTS.equals(categoryId)) {
			products = productDao.selectAllProducts(start);
		} else {
			products = productDao.selectProducts(categoryId, start);
		}

		return products;
	}

	public int getProductTotalCountById(int categoryId) {
		Integer productTotalCount = productDao.selectProductCountById(categoryId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));

		return productTotalCount;
	}

	public Map<String, Object> getProductDetail(Integer displayInfoId) {
		Map<String, Object> displayInfoMap = new HashMap<>();

		List<ProductPriceDto> productPriceList = productDao.selectProductPrice(displayInfoId);
		displayInfoMap.put("productPriceId", productPriceList);

		List<CommentResponse> comments = commentService.getComments(displayInfoId);
		displayInfoMap.put("comments", comments);

		Double averageScore = productDao.selectAverageScore(displayInfoId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_AVERAGE_SCORE_NOT_FOUND));
		displayInfoMap.put("averageScore", averageScore);

		return displayInfoMap;
	}
}
