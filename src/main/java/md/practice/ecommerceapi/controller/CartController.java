package md.practice.ecommerceapi.controller;

import lombok.AllArgsConstructor;
import md.practice.ecommerceapi.dto.CartDTO;
import md.practice.ecommerceapi.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    private String getToken(String token){
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }

    @GetMapping()
    public ResponseEntity<CartDTO> getCart(@RequestHeader("Authorization") String token){
        String jwt = getToken(token);
        return ResponseEntity.ok(cartService.getCart(jwt));
    }

    @PostMapping()
    public ResponseEntity<?> createCart(@RequestBody Set<Long> productIds,
                                        @RequestHeader("Authorization") String token) {
        String jwt = getToken(token);
        cartService.createCart(productIds, jwt);
        return ResponseEntity.status(201).body("Cart created");
    }

    @PutMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Set<Long> productIds,
                                       @RequestHeader("Authorization") String token){
        String jwt = getToken(token);
        cartService.addToCart(productIds, jwt);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody Set<Long> productIds,
                                            @RequestHeader("Authorization") String token){
        String jwt = getToken(token);
        cartService.removeFromCart(productIds, jwt);
        return ResponseEntity.ok("Item(s) removed" + " " + productIds);
    }
}
