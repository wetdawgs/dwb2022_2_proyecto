package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;

@Service
public class SvcCategoryImp implements SvcCategory {

	@Autowired
	RepoCategory repo;
	
	@Override
	public List<Category> getCategories() {
		return repo.findByStatus(1);
	}

	@Override
	public Category getCategory(Integer id) {
		Category category = repo.getCategory(id);
		if (category != null)
			return category;
		else
			throw new ApiException(HttpStatus.NOT_FOUND, "category does not exist");
	}

	@Override
	public ApiResponse createCategory(Category in) {
		in.setStatus(1);
		try {
			repo.save(in);
		}catch (DataIntegrityViolationException e) {
			if (e.getLocalizedMessage().contains("category"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "category already exist");
		}
		return new ApiResponse("category created");
	}

	@Override
	public ApiResponse updateCategory(Category in, Integer id) {
		try {
			repo.updateCategory(in.getCategory());
		}catch (DataIntegrityViolationException e) {
			if (e.getLocalizedMessage().contains("category"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "category rfc already exist");
		}
		return new ApiResponse("category updated");
	}

	@Override
	public ApiResponse deleteCategory(Integer id) {
		if (repo.deleteCategory(id) > 0)
			return new ApiResponse("category removed");
		else
			throw new ApiException(HttpStatus.BAD_REQUEST, "category cannot be deleted");
	}

}
