package microservices.order_service.service;

import lombok.AllArgsConstructor;
import microservices.order_service.dto.PageResponse;
import microservices.order_service.entity.Order;
import microservices.order_service.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public PageResponse findByRestaurantId(Map<String, Object> params) {
        Long restaurantId = (Long) params.get("restaurantId");
        Integer pageNo = (Integer) params.get("pageNo");
        Integer pageSize = (Integer) params.get("pageSize");
        String sortBy = (String) params.get("sortBy");

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.findByRestaurantId(restaurantId, pageable);

        return PageResponse.fromPage(orderPage);
    }
}
