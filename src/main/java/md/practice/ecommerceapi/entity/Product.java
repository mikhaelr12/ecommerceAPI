package md.practice.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "products")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "product_name" ,nullable = false)
    private String productName;

    @Column(name = "price")
    private Double price;
}
