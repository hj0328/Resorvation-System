package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.product.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * categoryId 상품의 start 기준 상품목록과 categoryId의 상품 수를 조회
	 * 만약, categoryId가 0이라면 전체 상품을 대상으로 조회한다.
	 * <p>
	 * 조회 대상은 가장 최근 출시된 상품순서대로 정렬
	 *
	 * @param categoryId 카테고리 아이디
	 * @param start      조회 시작 위치
	 */
	@GetMapping
	public ResponseEntity<ProductListResponse> getProduct(
			@RequestParam(required = false, defaultValue = "0") Long categoryId,
			@RequestParam(required = false, defaultValue = "0") Integer start) {

		List<ProductResponse> products = productService.getPagedProductsByCategoryId(categoryId, start);
		Long productTotalCount = productService.getProductCountByCategoryId(categoryId);

		ProductListResponse response = ProductListResponse.builder()
				.products(products)
				.totalProductCount(productTotalCount)
				.build();
		return ResponseEntity.ok(response);
	}

	/**
	 * 실시간 예매 인기 순위
	 * 현재 가장 많이 예매한 순위 상위 10개 조회
	 */
	@GetMapping("/popular-products")
	public List<PopularProductResponse> getRealTimePopularProduct(
			@RequestParam(required = false, defaultValue = "0") Integer startPage
	) {
		return productService.getRealTimePopularProduct(startPage);
	}


	/**
	 * product schedule (시간, 남은 좌석) 정보 조회
	 */
	@GetMapping("/schedule")
	public ResponseEntity<ProductSeatScheduleListResponse> getProductSeatSchedule(
			@RequestParam Long productId) {

		List<ProductSeatScheduleResponse> scheduleListList = productService.getProductSeatScheduleList(productId);
		ProductSeatScheduleListResponse resp = ProductSeatScheduleListResponse.builder()
				.productId(productId)
				.productSeatScheduleList(scheduleListList)
				.build();
		return ResponseEntity.ok(resp);
	}

	/**
	 * product 상세 조회(좌석, 가격 종류, 스케줄, 장소)
	 */
	@GetMapping("/detail-info")
	public ResponseEntity<ProductDetailResponse> getProductDetailInfo(@RequestParam Long productId) {
		return ResponseEntity.ok(
				productService.getProductDetailInfo(productId)
		);
	}

	/**
	 * product 등록
	 * product 정보, 좌석 등급별 금액
	 */
	@PostMapping("/new")
	public ResponseEntity<ProductResponse> registerProduct(
			@Valid @RequestBody ProductRegisterRequest request) {

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productService.addNewProduct(request));
	}

	/**
	 * product schedule 등록
	 * 좌석, 시간표 정보를 저장
	 */
	@PostMapping("/{productId}/schedule")
	public ResponseEntity<ProductSeatScheduleResponse> registerProductQuantitySchedule(
			@PathVariable Long productId,
			@Valid @RequestBody ProductSeatScheduleRequest request
	) {

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productService.addProductSeatSchedule(productId, request));
	}

	/**
	 * product schedule 수정
	 */
	@PutMapping("/{productId}/schedule/{productSeatScheduleId}")
	public ResponseEntity<ProductSeatScheduleResponse> updateProductSeatSchedule(
			@PathVariable Long productId,
			@PathVariable Long productSeatScheduleId,
			@Valid @RequestBody ProductSeatScheduleUpdateRequest request
	) {

		return ResponseEntity.ok(
				productService.updateProductSeatSchedule(productId, productSeatScheduleId, request)
		);
	}

	/**
	 * product 검색
	 * 모든 product를 조회하는 것을 대신하여 최대 100개까지만 검색하도록 제한
	 */
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProduct(
			@RequestParam String title) {

		return ResponseEntity.ok(
				productService.searchProductByTitle(title)
		);
	}

	/**
	 * product 수정
	 * category, product
	 */
	@PutMapping("/{productId}")
	public ResponseEntity<ProductResponse> updateProduct(
			@PathVariable Long productId,
			@RequestBody ProductRequest productRequest
	) {

		return ResponseEntity.ok(
				productService.updateProduct(productId, productRequest)
		);
	}

}
