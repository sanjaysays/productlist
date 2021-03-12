package com.tnf.productlist.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnf.productlist.dao.ProductRepository;
import com.tnf.productlist.entity.Product;
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
	public Optional<Product> getProductById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
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
	public void updateProduct(Optional<Product> product, String name, String desc, String currency, double price) {
		if (!Product.CURRENCY.equals(currency)) {
            price = curr.convert(currency, Product.CURRENCY, price);
        }

        // Round up only 2 decimals...
        price = (double) Math.round(price * 100) / 100;

        product.get().setName(name);
        product.get().setDescription(desc);
        product.get().setPrice(price);
        repo.save(product.get());
	}

}
