package kr.or.connect.reservation.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryDao categoryDao;
	
	public List<CategoryItemDto> getCategories() {
		return categoryDao.selectAll();
	}
}
