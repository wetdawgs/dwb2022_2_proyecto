package com.invoice.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoice.api.controller.CtrlCart;
import com.invoice.api.controller.CtrlInvoice;
import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoProduct;
import com.invoice.api.entity.Cart;
import com.invoice.api.entity.Invoice;
import com.invoice.api.entity.Item;
import com.invoice.api.repository.RepoInvoice;
import com.invoice.api.repository.RepoItem;
import com.invoice.configuration.client.ProductClient;
import com.invoice.exception.ApiException;

@Service
public class SvcInvoiceImp implements SvcInvoice {

	@Autowired
	RepoInvoice repo;
	
	@Autowired
	RepoItem repoItem;
	
	@Autowired
	SvcCart svc;
	
	@Autowired 
	ProductClient productCl;
	
	@Autowired
	CtrlCart ctrlCart;
	
	@Autowired
	CtrlInvoice ctrlInvoice;

	@Override
	public List<Invoice> getInvoices(String rfc) {
		return repo.findByRfcAndStatus(rfc, 1);
	}

	@Override
	public List<Item> getInvoiceItems(Integer invoice_id) {
		return repoItem.getInvoiceItems(invoice_id);
	}

	@Override
	public ApiResponse generateInvoice(String rfc) {
		/*
		 * Sprint 3 - Requerimiento 5
		 * Implementar el método para generar una factura 
		 */
		List<Cart> cart = svc.getCart(rfc);
		if (cart.isEmpty()) 
			throw new ApiException(HttpStatus.NOT_FOUND, "cart has no items");
		
		double total = 0, taxes = 0, subtotal = 0;
		
		for (Cart c : cart) {
			Item i = new Item();
			DtoProduct p = productCl.getProduct(c.getGtin()).getBody();
			i.setUnit_price(p.getPrice());
			i.setTotal(p.getPrice() * c.getQuantity());
			i.setTaxes(i.getTotal() * 0.16);
			i.setSubtotal(i.getTotal() - i.getTaxes());
			repoItem.save(i);
			//items.add(i);
			total += i.getTotal();
			taxes += i.getTaxes();
			subtotal += i.getSubtotal();
			productCl.updateProductStock(p.getGtin(), c.getQuantity());
		}
		
		LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();
        LocalDateTime fecha = LocalDateTime.of(hoy, ahora);
        
		Invoice invoice = new Invoice();
		invoice.setRfc(rfc);
		invoice.setSubtotal(subtotal);
		invoice.setTaxes(taxes);
		invoice.setTotal(total);
		invoice.setCreated_at(fecha);
		repo.save(invoice);
		
		ctrlCart.deleteCart(rfc);
		
		return new ApiResponse("invoice generated");
	}

}
