package com.invoice.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.invoice.api.entity.Cart;

@Repository
public interface RepoCart extends JpaRepository<Cart, Integer>{

	List<Cart> findByRfcAndStatus(String rfc, Integer status);
	
	@Query(value = "SELECT * FROM cart WHERE gtin = :gtin AND status = 1", nativeQuery = true)
	Cart findByCartGtin(String gtin);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE cart SET quantity = :quantity WHERE gtin = :gtin AND status = 1", nativeQuery = true)
	Integer updateCartQuantity(@Param("gtin") String gtin, @Param("quantity") Integer quantity);

	@Modifying
	@Transactional
	@Query(value ="UPDATE cart SET status = 0 WHERE cart_id = :cart_id AND status = 1", nativeQuery = true)
	Integer removeFromCart(@Param("cart_id") Integer cart_id);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE cart SET status = 0 WHERE rfc = :rfc AND status = 1", nativeQuery = true)
	Integer clearCart(@Param("rfc") String rfc);
}
