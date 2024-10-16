package md.practice.ecommerceapi.controller;

import lombok.AllArgsConstructor;
import md.practice.ecommerceapi.dto.CartDTO;
import md.practice.ecommerceapi.dto.ProductDTO;
import md.practice.ecommerceapi.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    private String getToken(String token){
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productsService.getAllProducts());
    }

    @PostMapping("/cart")
    public ResponseEntity<?> createCart(@RequestBody CartDTO cartDTO,
                                       @RequestHeader("Authorization") String token) {
        String jwt = getToken(token);
        productsService.createCart(cartDTO, jwt);
        return ResponseEntity.ok("Cart created");
    }

    @PutMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO,
                                       @RequestHeader("Authorization") String token){
        String jwt = getToken(token);
        productsService.addToCart(cartDTO, jwt);
        return ResponseEntity.ok("Item(s) added" + " " +cartDTO.getProductsId());
    }

    @PutMapping("/cart/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody CartDTO cartDTO,
                                            @RequestHeader("Authorization") String token){
        String jwt = getToken(token);
        productsService.removeFromCart(cartDTO, jwt);
        return ResponseEntity.ok("Item(s) removed" + " " +cartDTO.getProductsId());
    }
}
