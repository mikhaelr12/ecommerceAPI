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

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productsService.getAllProducts());
    }
}
