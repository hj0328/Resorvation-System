package kr.or.connect.reservation.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping(path = "/categories")
	public Map<String, Object> getCategories() {
		List<CategoryItemDto> categories = categoryService.getCategories();
		Map<String, Object> map = new HashMap<>();
		map.put("items", categories);
		return map;
	}
}
