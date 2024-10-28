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
        cartServiceImpl.createCart(Set.of(1L), "valid-jwt");

        // Verify that the cart was saved
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void alreadyInCart() {
        User user = new User();
        user.setId(1L);
        when(cartServiceImpl.getUser(anyString())).thenReturn(user);
        Cart existingCart = Cart.builder()
                .status(Status.IN_PROGRESS)
                .userId(1L)
                .products(Set.of(new Product(1L, "Product 1", 50.0)))
                .total(50.0)
                .build();

        when(cartRepository.getByUserId(1L)).thenReturn(existingCart);
        assertThrows(CartException.class, () -> cartServiceImpl.createCart(Set.of(2L), "valid"),
                "Cart already exists");
        verify(cartRepository, never()).save(any(Cart.class));
    }
}
