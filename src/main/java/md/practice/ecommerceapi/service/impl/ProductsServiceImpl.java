package md.practice.ecommerceapi.service.impl;

import lombok.AllArgsConstructor;
import md.practice.ecommerceapi.dto.ProductDTO;
import md.practice.ecommerceapi.entity.Product;
import md.practice.ecommerceapi.repository.ProductsRepository;
import md.practice.ecommerceapi.service.ProductsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(product -> ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build()).collect(Collectors.toList());
    }
}
