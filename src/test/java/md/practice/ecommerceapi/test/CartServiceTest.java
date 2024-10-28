package md.practice.ecommerceapi.test;

import md.practice.ecommerceapi.dto.CartDTO;
import md.practice.ecommerceapi.entity.Cart;
import md.practice.ecommerceapi.entity.Product;
import md.practice.ecommerceapi.entity.User;
import md.practice.ecommerceapi.enums.Status;
import md.practice.ecommerceapi.exception.CartException;
import md.practice.ecommerceapi.repository.CartRepository;
import md.practice.ecommerceapi.repository.ProductsRepository;
import md.practice.ecommerceapi.repository.UserRepository;
import md.practice.ecommerceapi.service.impl.CartServiceImpl;
import md.practice.ecommerceapi.service.impl.JwtServiceImpl;
import org.hibernate.sql.ast.tree.expression.CaseSimpleExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private JwtServiceImpl jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductsRepository productsRepository;
    @InjectMocks
    private CartServiceImpl cartServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCart() {
        when(jwtService.extractUsername("valid-jwt")).thenReturn("test_user");
        User user = new User();
        user.setId(1L);
        user.setUsername("test_user");
        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(user));

        when(cartRepository.getByUserId(1L)).thenReturn(null);

        Product product = new Product();
        product.setId(1L);
        product.setProductName("test");
        product.setPrice(10.50);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));

        // Call the createCart method
        cartServiceImpl.addToCart(Set.of(1L), "valid-jwt");

        // Verify that the cart was saved
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void itemAlreadyInCart() {
        when(jwtService.extractUsername("valid-jwt")).thenReturn("test_user");
        User user = new User();
        user.setId(1L);
        user.setUsername("test_user");
        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(user));

        //existing products
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setProductName("test");
        existingProduct.setPrice(10.50);

        Cart cart = new Cart();
        cart.setProducts(Set.of(existingProduct));
        cart.setTotal(10.50);

        //adding new product
        when(cartRepository.getByUserId(1L)).thenReturn(cart);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        assertThrows(CartException.class, () ->
                cartServiceImpl.addToCart(Set.of(1L), "valid-jwt")
        );

        verify(cartRepository, never()).save(any(Cart.class));
    }
}
