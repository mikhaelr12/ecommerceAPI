package md.practice.ecommerceapi.service.impl;

import lombok.AllArgsConstructor;
import md.practice.ecommerceapi.dto.ProductDTO;
import md.practice.ecommerceapi.entity.Product;
import md.practice.ecommerceapi.exception.ProductException;
import md.practice.ecommerceapi.repository.ProductsRepository;
import md.practice.ecommerceapi.service.ProductManagerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductManagerServiceImpl implements ProductManagerService {

    private final ProductsRepository productsRepository;

    @Override
    public void addProduct(ProductDTO productDTO) {
        productsRepository.save(Product.builder()
                        .productName(productDTO.getProductName())
                        .price(productDTO.getPrice())
                .build());
    }

    @Override
    public void removeProduct(Long id) {
        Optional<Product> product = productsRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductException("No product found with id " + id);
        }
        productsRepository.delete(product.get());
    }

    @Override
    public void udpatePrice(ProductDTO productDTO, Long id) {
        Optional<Product> product = productsRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductException("No product found with id " + id);
        }
        product.get().setPrice(productDTO.getPrice());
        productsRepository.save(product.get());
    }
}
