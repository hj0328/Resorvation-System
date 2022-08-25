package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.ProductItem;
import kr.or.connect.reservation.service.ProductService;

@RestController
@RequestMapping(path="/api")
public class ProductCategory {
	private final ProductService productService; 

	@Autowired
	public ProductCategory(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(path = "/products")
	public Map<String, Object> getProducts(@RequestParam(required = false) Integer categoryId
			, @RequestParam(required = false, defaultValue = "0") Integer start) {

		List<ProductItem> products = productService.getProducts(categoryId, start);
		int productCntById = productService.getProductCntById(categoryId);
		
		Map<String, Object> map = new HashMap<>();
		map.put("items", products);
		map.put("totalCount", productCntById);
		return map;
	}
}
