package md.practice.ecommerceapi.service;

import md.practice.ecommerceapi.dto.ProductDTO;

public interface ProductManagerService {
    void addProduct(ProductDTO productDTO);

    void removeProduct(Long id);

    void udpatePrice(ProductDTO productDTO, Long id);
}
