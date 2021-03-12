package com.tnf.productlist.service;

import java.util.List;
import java.util.Optional;

import com.tnf.productlist.entity.Product;

/**
 * Service for {@link Product} entity
 *
 * @author
 */
public interface ProductService {
	
	/**
     * Gets all products present in the system.
     *
     * @param page the page to fetch results from
     * @return 
     */
	List<Product> getAllProductList();
	
	 /**
     * Gets a specific product by looking for its id.
     *
     * @param id the id of the product to look for
     * @return the product (if any)
     */
    Optional<Product> getProductById(Long id);

    /**
     * Creates a product.
     * If the currency is not 'GBP' then a Currency Exchange
     * will be performed.
     *
     * @param name the name of the product
     * @param currency the currency of the product
     * @param price the price of the product
     * @return the new product
     */
    Product createProduct(String name, String desc, String currency, double price);

    /**
     * Updates an existing product.
     * If the currency is not 'GBP' then a Currency Exchange
     * will be performed.
     *
     * @param product the product to update
     * @param name the new product name
     * @param currency the new product currency
     * @param price the new product price
     */
    void updateProduct(Optional<Product> product, String name, String desc, String currency, double price);

}
