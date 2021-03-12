package com.tnf.productlist.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.productlist.dto.ProductDto;
import com.tnf.productlist.entity.Product;
import com.tnf.productlist.service.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	
	@RequestMapping(method = RequestMethod.GET)
    public List<Product> retrieveAllProducts() {
		
        // Getting all products 
        final List<Product> products = productService.getAllProductList();

        return products;
    }
	
	 @RequestMapping(method = RequestMethod.POST)
	    public void createProduct(@RequestBody @Validated ProductDto request) {

		 // Creating a new product
	        final Product product = productService.createProduct(request.getName(), request.getDescription(), request.getCurrency(), request.getPrice());

	    }

	    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	    public void updateProduct(@PathVariable Long id, @RequestBody @Validated ProductDto request) {
	        // Getting the requiring product; or throwing exception if not found
	        final Optional<Product> product = productService.getProductById(id);

	        // Updating a product in the application...
	        productService.updateProduct(product, request.getName(), request.getDescription(), request.getCurrency(), request.getPrice());

	    }

}
