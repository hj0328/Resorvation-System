package kr.or.connect.reservation.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDao categoryDao;
	
	@Override
	public List<CategoryItemDto> getCategories() {
		return categoryDao.selectAll();
	}
}
