package md.practice.ecommerceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import md.practice.ecommerceapi.enums.Status;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "histories")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_id_seq")
    @SequenceGenerator(name = "history_id_seq", sequenceName = "history_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id", nullable = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_HISTORY"))
    private Long userId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "history_products",
            joinColumns =@JoinColumn(name = "history_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "finished_at", nullable = false)
    private LocalDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
