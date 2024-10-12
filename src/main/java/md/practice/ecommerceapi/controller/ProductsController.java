package md.practice.ecommerceapi.controller;

import md.practice.ecommerceapi.dto.ProductDTO;
import md.practice.ecommerceapi.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private  ProductsService productsService;
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productsService.getAllProducts());
    }
}
