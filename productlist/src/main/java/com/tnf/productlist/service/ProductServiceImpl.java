package com.tnf.productlist.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnf.productlist.dao.ProductRepository;
import com.tnf.productlist.entity.Product;
import com.tnf.productlist.exception.ResourceNotFoundException;
import com.tnf.productlist.util.CurrencyExchangeUtil;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

	@Autowired
    private ProductRepository repo;
    
    private CurrencyExchangeUtil curr = new CurrencyExchangeUtil();
    
	@Override
	public List<Product> getAllProductList() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		Product product = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found Tutorial with id = " + id));
		
		return product;
	}

	@Override
	public Product createProduct(String name, String desc, String currency, double price) {
		if (!Product.CURRENCY.equals(currency)) {
            price = curr.convert(currency, Product.CURRENCY, price);
        }

        // Round up only 2 decimals...
        price = (double) Math.round(price * 100) / 100;

        Product product = new Product();
        product.setName(name);
        product.setDescription(desc);
        product.setPrice(price);

        return repo.save(product);
	}

	@Override
	public void updateProduct(Product product, String name, String desc, String currency, double price) {
		if (!Product.CURRENCY.equals(currency)) {
            price = curr.convert(currency, Product.CURRENCY, price);
        }

        // Round up only 2 decimals...
        price = (double) Math.round(price * 100) / 100;

        product.setName(name);
        product.setDescription(desc);
        product.setPrice(price);
        repo.save(product);
	}

}
