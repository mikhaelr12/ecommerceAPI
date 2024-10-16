package md.practice.ecommerceapi.repository;

import md.practice.ecommerceapi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> getAllCartsByUserId(Long id);

    Cart getByUserId(Long id);
}
