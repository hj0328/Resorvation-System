package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.product.dto.ProductItemDto;
import kr.or.connect.reservation.utils.UtilConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
		List<ProductItemDto> products = productService.getProducts(categoryId, start);
		int totalProductCount = productService.getProductTotalCountById(categoryId);

		Map<String, Object> map = new HashMap<>();
		map.put(UtilConstant.ITEMS, products);
		map.put(UtilConstant.TOTAL_COUNT, totalProductCount);
		return map;
	}
	
}
