package md.practice.ecommerceapi.service.impl;

import lombok.AllArgsConstructor;
import md.practice.ecommerceapi.dto.CartDTO;
import md.practice.ecommerceapi.dto.ProductDTO;
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
import md.practice.ecommerceapi.service.ProductsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final JwtServiceImpl jwtService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private User getUser(String jwtToken){
        String username = jwtService.extractUsername(jwtToken);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("No user found"));
    }

    private Set<Product> getProducts(CartDTO cartDTO){
        Set<Product> products = new HashSet<>();
        for(Long productsId : cartDTO.getProductsId()){
            Product product = productsRepository.findById(productsId)
                    .orElseThrow(() -> new ProductException("No product found"));
            products.add(product);
        }
        return products;
    }

    private Double calculatePrice(Set<Product> products){
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(product -> ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void createCart(CartDTO cartDTO, String jwt) {
        User user = getUser(jwt);
        List<Cart> cart = cartRepository.getAllCartsByUserId(user.getId());
        boolean inProgress = cart.stream()
                .anyMatch(progress -> progress.getStatus() == Status.IN_PROGRESS);
        if(inProgress){
            throw new CartException("Cart is already in progress, finish with it before making a new one");
        }
        Set<Product> products = getProducts(cartDTO);
        cartRepository.save(Cart.builder()
                        .status(Status.IN_PROGRESS)
                        .products(products)
                        .userId(user.getId())
                        .total(calculatePrice(products))
                .build());
    }

    @Override
    public void addToCart(CartDTO cartDTO, String jwt) {
        User user = getUser(jwt);
        Cart cart = cartRepository.getByUserId(user.getId());
        Set<Product> currentProducts = cart.getProducts();
        Set<Product> products = getProducts(cartDTO);
        currentProducts.addAll(products);
        cart.setTotal(cart.getTotal() + calculatePrice(products));
        cart.setProducts(currentProducts);
        cartRepository.save(cart);
    }

    @Override
    public void removeFromCart(CartDTO cartDTO, String jwt) {
        User user = getUser(jwt);
        Cart cart = cartRepository.getByUserId(user.getId());
        Set<Product> currentProducts = cart.getProducts();
        Set<Product> products = getProducts(cartDTO);
        currentProducts.removeAll(products);
        cart.setTotal(cart.getTotal() - calculatePrice(products));
        cart.setProducts(currentProducts);
        if(cart.getProducts().isEmpty()){
            cartRepository.delete(cart);
        }
        else{
            cartRepository.save(cart);
        }
    }
}
