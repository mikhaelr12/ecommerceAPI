package md.practice.ecommerceapi.service;

import md.practice.ecommerceapi.dto.CartDTO;

import java.util.List;
import java.util.Set;

public interface CartService {
    void addToCart(Set<Long> productIds, String jwt);

    void removeFromCart(Set<Long> productIds, String jwt);

    CartDTO getCart(String jwt);
}
