package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.ProductItemDto;
import kr.or.connect.reservation.service.ProductService;

@RestController
@RequestMapping(path="/api/products")
public class ProductCategoryController {
	private final ProductService productService;

	@Autowired
	public ProductCategoryController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public Map<String, Object> getProducts(@RequestParam(required = false) int categoryId
			, @RequestParam(required = false, defaultValue = "0") int start) {

		List<ProductItemDto> products = productService.getProducts(categoryId, start);
		int productCntById = productService.getProductCountById(categoryId);

		Map<String, Object> map = new HashMap<>();
		map.put("items", products);
		map.put("totalCount", productCntById);
		return map;
	}

	@GetMapping(path = "/{displayInfoId}")
	public Map<String, Object> getDisplayInfo(@PathVariable int displayInfoId) {
		return productService.getDisplayInfo(displayInfoId);
	}
}
