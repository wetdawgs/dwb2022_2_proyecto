package com.invoice.configuration.client;

import org.springframework.http.ResponseEntity;

/*
 * Sprint 1 - Requerimiento 2
 * Actualizar método getProduct para obtener la información necesaria de un producto
 */

/*
 * Sprint 3 - Requerimiento 5
 * Agregar método updateProductStock para actualizar el stock de productos
 */

public interface ProductClient {

	public ResponseEntity<Object> getProduct(String gtin);

}
