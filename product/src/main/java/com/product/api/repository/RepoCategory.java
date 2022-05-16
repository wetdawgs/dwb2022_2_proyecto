package com.product.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Category;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer>{

	List<Category> findByStatus(@Param("status") Integer status);
	
	@Query(value ="SELECT * FROM category WHERE category_id = :category_id AND status = 1", nativeQuery = true)
	Category getCategory(Integer category_id);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE category SET category = :category WHERE status = 1", nativeQuery = true)
	Integer updateCategory(@Param("category") String category);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE category SET status = 0 WHERE category_id = :category_id AND status = 1", nativeQuery = true)
	Integer deleteCategory(@Param("category_id") Integer category_id);
}
