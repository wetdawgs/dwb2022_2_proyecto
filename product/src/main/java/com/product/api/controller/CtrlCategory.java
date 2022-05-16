package com.product.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import com.product.exception.ApiException;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

	@Autowired
	SvcCategory svcCategory;
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategories(){
		return new ResponseEntity<>(svcCategory.getCategories(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") int id){
		return new ResponseEntity<>(svcCategory.getCategory(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category in, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(svcCategory.createCategory(in),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable("id") Integer id, @Valid @RequestBody Category in, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(svcCategory.updateCategory(in, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Integer id){
		return new ResponseEntity<>(svcCategory.deleteCategory(id), HttpStatus.OK);
	}
}
