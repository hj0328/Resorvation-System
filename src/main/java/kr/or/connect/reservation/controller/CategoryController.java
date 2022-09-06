package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.CategoryItemDto;
import kr.or.connect.reservation.service.CategoryService;

@RestController
@RequestMapping(path="/api")
public class CategoryController {
	
	private final CategoryService categoryService; 
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping(path = "/categories")
	public Map<String, Object> getCategories() {
		List<CategoryItemDto> categories = categoryService.getCategories();
		Map<String, Object> map = new HashMap<>();
		map.put("items", categories);
		return map;
	}
}
