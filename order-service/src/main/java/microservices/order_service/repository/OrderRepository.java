package microservices.order_service.repository;

import microservices.order_service.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page findByRestaurantId(Long restaurantId, Pageable pageable);
}
