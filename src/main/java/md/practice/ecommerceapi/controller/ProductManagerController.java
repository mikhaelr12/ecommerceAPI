package md.practice.ecommerceapi.controller;

import lombok.AllArgsConstructor;
import md.practice.ecommerceapi.dto.ProductDTO;
import md.practice.ecommerceapi.service.ProductManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-manager")
@AllArgsConstructor
public class ProductManagerController {

    private ProductManagerService productManagerService;
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        productManagerService.addProduct(productDTO);
        return ResponseEntity.ok("Product created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productManagerService.removeProduct(id);
        return ResponseEntity.ok("Product deleted with id " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        productManagerService.updatePrice(productDTO, id);
        return ResponseEntity.ok("Product updated with id " + id);
    }
}
