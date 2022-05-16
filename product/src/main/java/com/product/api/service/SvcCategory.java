package com.product.api.service;

import java.util.List;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;

public interface SvcCategory {

	public List<Category> getCategories();
	public Category getCategory(Integer id);
	public ApiResponse createCategory(Category in);
	public ApiResponse updateCategory(Category in, Integer id);
	public ApiResponse deleteCategory(Integer id);

}
