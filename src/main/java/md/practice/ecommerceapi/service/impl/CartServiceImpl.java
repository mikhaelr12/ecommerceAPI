package md.practice.ecommerceapi.service.impl;

import lombok.AllArgsConstructor;
import md.practice.ecommerceapi.dto.CartDTO;
import md.practice.ecommerceapi.entity.Cart;
import md.practice.ecommerceapi.entity.Product;
import md.practice.ecommerceapi.entity.User;
import md.practice.ecommerceapi.enums.Status;
import md.practice.ecommerceapi.exception.CartException;
import md.practice.ecommerceapi.exception.ProductException;
import md.practice.ecommerceapi.exception.UserException;
import md.practice.ecommerceapi.repository.CartRepository;
import md.practice.ecommerceapi.repository.ProductsRepository;
import md.practice.ecommerceapi.repository.UserRepository;
import md.practice.ecommerceapi.service.CartService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductsRepository productsRepository;
    private final JwtServiceImpl jwtService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public User getUser(String jwtToken) {
        String username = jwtService.extractUsername(jwtToken);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("No user found"));
    }

    public Set<Product> getProductsByIds(Set<Long> productIds) {
        Set<Product> products = new HashSet<>();
        for (Long productId : productIds) {
            Product product = productsRepository.findById(productId)
                    .orElseThrow(() -> new ProductException("Product not found"));
            products.add(product);
        }
        return products;
    }

    public Double calculatePrice(Set<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }


    @Override
    public void addToCart(Set<Long> productIds, String jwt) {
        User user = getUser(jwt);
        Cart cart = cartRepository.getByUserId(user.getId());

        if (cart != null) {
            Set<Product> currentProducts = cart.getProducts();
            Set<Product> newProducts = getProductsByIds(productIds);

            for (Product newProduct : newProducts) {
                if (currentProducts.contains(newProduct)) {
                    throw new CartException("Product with ID " + newProduct.getId() + " is already in the cart.");
                }
            }

            currentProducts.addAll(newProducts);
            cart.setTotal(cart.getTotal() + calculatePrice(newProducts));
            cartRepository.save(cart);
        } else {
            Set<Product> products = getProductsByIds(productIds);
            Cart newCart = Cart.builder()
                    .status(Status.IN_PROGRESS)
                    .products(products)
                    .userId(user.getId())
                    .total(calculatePrice(products))
                    .build();
            cartRepository.save(newCart);
        }
    }




    @Override
    public void removeFromCart(Set<Long> productIds, String jwt) {
        User user = getUser(jwt);
        Cart cart = cartRepository.getByUserId(user.getId());
        if (cart == null) {
            throw new CartException("Cart not found");
        }

        Set<Product> productsToRemove = getProductsByIds(productIds);

        cart.getProducts().removeAll(productsToRemove);

        cart.setTotal(cart.getTotal() - calculatePrice(productsToRemove));

        if (cart.getProducts().isEmpty()) {
            cartRepository.delete(cart);
        } else {
            cartRepository.save(cart);
        }
    }

    @Override
    public CartDTO getCart(String jwt) {
        User user = getUser(jwt);
        Cart cart = cartRepository.getByUserId(user.getId());
        if (cart == null) {
            throw new CartException("Cart not found");
        }
        Set<Product> products = cart.getProducts();
        Set<Long> productIds = products.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
        return CartDTO.builder()
                .id(cart.getId())
                .productsId(productIds)
                .total(cart.getTotal())
                .build();
    }
}

