package microservices.order_service.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private Long orderId;

    private Long menuItemId;

    private BigDecimal price;
}