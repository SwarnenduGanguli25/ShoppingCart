package com.project.ShoppingCart.service;

import com.project.ShoppingCart.dto.AddToCartDto;
import com.project.ShoppingCart.dto.ProductDto;
import com.project.ShoppingCart.exceptions.ProductNotExistsException;
import com.project.ShoppingCart.model.Category;
import com.project.ShoppingCart.model.Product;
import com.project.ShoppingCart.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public void addProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setCategory(category);
        productRepo.save(product);
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setImageURL(product.getImageURL());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setId(product.getId());
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepo.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : allProducts) {
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }

    public void updateProduct(ProductDto productDto, int productId) throws Exception {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new Exception("Product is not present");
        }
        Product product = optionalProduct.get();
        product.setName(productDto.getName());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepo.save(product);
    }

    public Product findById(Integer productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("Product id is invalid : " + productId);
        }
        return optionalProduct.get();
    }

    public boolean outOfStock(AddToCartDto addToCartDto) throws ProductNotExistsException {
        Product product = findById(addToCartDto.getProductId());
        return (product.getQuantity() == 0);
    }

    public boolean checkStockAvailability(AddToCartDto addToCartDto) throws ProductNotExistsException {
        Product product = findById(addToCartDto.getProductId());
        return (product.getQuantity() < addToCartDto.getQuantity());
    }

    public int getAvailableStock(AddToCartDto addToCartDto) throws ProductNotExistsException {
        Product product = findById(addToCartDto.getProductId());
        return product.getQuantity();
    }
}
