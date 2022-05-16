package com.invoice.api.dto;

/*
 * Sprint 1 - Requerimiento 2
 * Agregar atributos de clase para la validación del producto
 */
public class DtoProduct {
	
	private String gtin;
	
	private int stock;

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
