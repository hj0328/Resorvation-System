package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.CategoryItemDto;

public interface CategoryService {
	List<CategoryItemDto> getCategories();
}
