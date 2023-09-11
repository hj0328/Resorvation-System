package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.comment.CommentDao;
import kr.or.connect.reservation.domain.comment.dto.CommentDto;
import kr.or.connect.reservation.domain.comment.dto.CommentImageDto;
import kr.or.connect.reservation.domain.comment.CommentService;
import kr.or.connect.reservation.domain.product.dto.ProductItemDto;
import kr.or.connect.reservation.utils.UtilConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * categoryId 상품의 start 기준 4건 상품목록과 categoryId의 전체 상품 수를 조회
	 * 만약, categoryId가 없다면 전체 상품을 대상으로 조회한다.
	 * @param categoryId 카테고리 아이디
	 * @param start 조회 시작 위치
	 */
	@GetMapping
	public Map<String, Object> getProducts(@RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false, defaultValue = "0") Integer start) {
		log.info("GET /api/products, categoryId:{}, start:{}", categoryId, start);

		List<ProductItemDto> products = productService.getProducts(categoryId, start);
		int totalProductCount = productService.getProductTotalCountById(categoryId);

		Map<String, Object> map = new HashMap<>();
		map.put(UtilConstant.ITMES, products);
		map.put(UtilConstant.TOTAL_COUNT, totalProductCount);
		return map;
	}

	/**
	 * displayInfoId 상품전시 정보 조회
	 * @param displayInfoId
	 */
	@GetMapping(path = "/{displayInfoId}")
	public Map<String, Object> getProductDetail(@PathVariable Integer displayInfoId) {
		log.info("GET /api/products/{displayInfoId}, displayInfoId:{}", displayInfoId);
		return productService.getProductDetail(displayInfoId);
	}

	@Service
	@RequiredArgsConstructor
	public static class CommentServiceImpl implements CommentService {

		private final CommentDao commentDao;

		@Override
		public List<CommentDto> getComments(int displayInfoId) {
			List<CommentDto> commentList = commentDao.selectComment(displayInfoId);
			for (CommentDto commentDto : commentList) {
				List<CommentImageDto> commentImageList = commentDao
						.selectCommentImageByCommentId(commentDto.getCommentId());
				commentDto.setCommentImages(commentImageList);
			}

			return commentList;
		}

	}
}
