package md.practice.ecommerceapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ProductDTO {
    private Long id;
    private String productName;
    private Double price;
}
