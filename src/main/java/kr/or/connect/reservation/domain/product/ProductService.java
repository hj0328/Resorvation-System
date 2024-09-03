package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.product.dao.*;
import kr.or.connect.reservation.domain.product.dto.*;
import kr.or.connect.reservation.domain.product.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static kr.or.connect.reservation.utils.UtilConstant.ALL_PRODUCTS;
import static kr.or.connect.reservation.utils.UtilConstant.PRODUCT_PAGE_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	//	private final CommentService commentService;
	private final ProductDao productDao;
	private final ProductRepository productRepository;
	private final ProductSeatScheduleRepository productSeatScheduleRepository;
	private final ProductPriceRepository productPriceRepository;
	private final PlaceRepository placeRepository;
	private final CategoryRepository categoryRepository;

	private final InMemoryPopularProduct inMemoryPopularProduct;
	private final RedisPopularProduct redisPopularProduct;

	public List<ProductResponse> getPagedProductsByCategoryId(Long categoryId, Integer start) {
		PageRequest pageRequest = PageRequest.of(start, PRODUCT_PAGE_SIZE
				, Sort.by(Sort.Direction.DESC, "releaseDate"));

		List<Product> products = Collections.emptyList();
		if (ALL_PRODUCTS.equals(categoryId)) {
			products = productRepository.findAll(pageRequest).getContent();
		} else {
			products = productRepository.findAllByCategoryId(categoryId, pageRequest).getContent();
		}
		return products.stream()
				.map(ProductResponse::of)
				.collect(Collectors.toList());
	}

	public Long getProductCountByCategoryId(Long categoryId) {
		if (ALL_PRODUCTS.equals(categoryId)) {
			return Long.valueOf(productRepository.count());
		}
		return productRepository.countByCategoryId(categoryId);
	}

	public ProductDetailResponse getProductDetailInfo(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));

		List<ProductPriceResponse> priceDtoList = product.getProductPriceList().stream()
				.map(ProductPriceResponse::of)
				.collect(Collectors.toList());

		String category = product.getCategory().getName().toString();
		return ProductDetailResponse.of(category, product, priceDtoList);
	}

	public List<ProductSeatScheduleResponse> getProductSeatScheduleList(Long productId) {
		List<ProductSeatSchedule> seatScheduleList = productSeatScheduleRepository.findAllByProductId(productId);

		return seatScheduleList.stream()
				.map(v -> ProductSeatScheduleResponse.of(v, v.getPlace()))
				.collect(Collectors.toList());
	}

	@Transactional
	public ProductResponse addNewProduct(ProductRegisterRequest request) {
		Category category = categoryRepository.getReferenceById(request.getCategoryId());

		Product product = request.toProduct();
		product.registerCategory(category);
		Product saveProduct = productRepository.save(product);

		List<ProductPrice> savedPriceList = request.getPriceList().stream()
				.map(v -> v.toProductPrice(saveProduct))
				.collect(Collectors.toList());

		List<ProductPrice> productPrices = productPriceRepository.saveAll(savedPriceList);

		return ProductResponse.of(saveProduct);
	}

	@Transactional
	public ProductSeatScheduleResponse addProductSeatSchedule(Long productId, ProductSeatScheduleRequest request) {
		ProductSeatSchedule requestSchedule = request.toProductSeatSchedule();

		registerScheduleToProduct(productId, requestSchedule);

		Place place = placeRepository.findById(request.getPlaceId())
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PLACE_NOT_FOUND));

		requestSchedule.updatePlace(place);
		ProductSeatSchedule saveProductSeatSchedule = productSeatScheduleRepository.save(requestSchedule);

		return ProductSeatScheduleResponse.of(saveProductSeatSchedule, place);
	}

	private void registerScheduleToProduct(Long productId, ProductSeatSchedule requestSchedule) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));

		List<ProductSeatSchedule> scheduleList = product.getProductSeatScheduleList();
		if (scheduleList.stream()
				.anyMatch(v -> v.equals(requestSchedule))) {
			throw new CustomException(CustomExceptionStatus.DUPLICATE_PRODUCT_SCHEDULE);
		}
		product.addProductSeatSchedule(requestSchedule);
	}

	public List<ProductResponse> searchProductByTitle(String title) {
		PageRequest pageRequest = PageRequest.
				of(0, PRODUCT_PAGE_SIZE, Sort.by(Sort.Direction.DESC, "releaseDate"));

		List<Product> foundProductList = productRepository
				.findByTitleStartsWith(title, pageRequest);

		return foundProductList.stream()
				.sorted(Comparator.comparing(Product::getReleaseDate,
								Comparator.reverseOrder())
						.thenComparing(Product::getId))
				.map(ProductResponse::of)
				.collect(Collectors.toList());
	}

	@Transactional
	public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));
		product.updateProduct(productRequest);

		product.getCategory().updateCategory(productRequest.getCategory());
		return ProductResponse.of(product);
	}

	@Transactional
	public ProductSeatScheduleResponse updateProductSeatSchedule(Long productId, Long productSeatScheduleId, ProductSeatScheduleUpdateRequest request) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));

		ProductSeatSchedule seatSchedule = productSeatScheduleRepository.findById(productSeatScheduleId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_SCHEDULE_NOT_FOUND));

		Long expectProductId = seatSchedule.getProduct().getId();
		if (!expectProductId.equals(product.getId())) {
			throw new CustomException(CustomExceptionStatus.INVALID_REQUEST_ERROR);
		}

		Place place = placeRepository.findById(request.getPlaceId())
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.PLACE_NOT_FOUND));

		seatSchedule.update(request.getEventDateTime(), request.getReservedQuantity(), place);

		return ProductSeatScheduleResponse.of(seatSchedule, place);
	}

	public List<PopularProductResponse> getRealTimePopularProduct(Integer startPage) {
		List<InMemoryProductDto> inMemoryProductDto = redisPopularProduct.getInMemoryProductDto();

		int offset = startPage * PRODUCT_PAGE_SIZE;
		int limit = PRODUCT_PAGE_SIZE;
		int fromIndex = offset;
		int toIndex = offset + limit;
		int idsSize = inMemoryProductDto.size();

		if (offset > idsSize) {
			return Collections.emptyList();
		}

		if (toIndex > idsSize) {
			toIndex = idsSize;
		}

		return inMemoryProductDto.subList(fromIndex, toIndex).stream()
				.map(PopularProductResponse::of)
				.collect(Collectors.toList());

//		local thread 코드
//		if (inMemoryPopularProduct.isEmpty()) {
//			log.info("get popular product from DB");
//
//			PageRequest pageRequest = PageRequest.of(startPage, PRODUCT_PAGE_SIZE);
//			List<PopularProductDto> popularProductDtos = productSeatScheduleRepository
//					.findPopularProductByReservation(pageRequest);
//
//			return popularProductDtos.stream()
//					.map(PopularProductResponse::of)
//					.collect(Collectors.toList());
//		}
//

//		return inMemoryPopularProduct.getProducts(startPage, PRODUCT_PAGE_SIZE);
	}
}
