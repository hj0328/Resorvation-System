package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.CategoryItem;

public interface CategoryService {
	List<CategoryItem> getCategories();
}
