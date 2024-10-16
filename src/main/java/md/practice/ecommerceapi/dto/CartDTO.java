package md.practice.ecommerceapi.dto;

import lombok.*;
import md.practice.ecommerceapi.enums.Status;

import java.util.Set;

@Getter @Setter
@Builder
public class CartDTO {
    private Long id;
    private Set<Long> productsId;
}
