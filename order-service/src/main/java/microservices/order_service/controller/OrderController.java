package microservices.order_service.controller;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import microservices.order_service.dto.ApiResponse;
import microservices.order_service.service.OrderService;
import microservices.order_service.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import microservices.order_service.entity.Order;
import microservices.order_service.repository.OrderItemRepository;
import microservices.order_service.repository.OrderRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@SecurityRequirement(name = "Keycloak")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    OrderService orderService;
    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_PAGE_SIZE = "10";
    private final String DEFAULT_SORT_BY = "endDate";
    KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("test")
    public String getTest() {
        return "fdsfdsfds";
    }

    @GetMapping
    public ApiResponse getTest2() {
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.SUCCESS)
                .data("get method")
                .build();
    }

    @PostMapping
    public ApiResponse postTest() {
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.SUCCESS)
                .data("post method")
                .build();
    }

    @PutMapping
    public ApiResponse putTest() {
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.SUCCESS)
                .data("put method")
                .build();
    }

    @DeleteMapping
    public ApiResponse deleteTest() {
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.SUCCESS)
                .data("delete method")
                .build();
    }

    @GetMapping("/{restaurantId}/list")
    // manager can access (suresh)
    public ApiResponse getOrders(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = DEFAULT_SORT_BY) String sortBy
    ) {
        Map<String, Object> args = new HashMap<>();
        args.put("orderService", orderService);
        args.put("restaurantId", restaurantId);
        args.put("pageNo", pageNo);
        args.put("pageSize", pageSize);
        args.put("sortBy", sortBy);

//        NotificationEvent notificationEvent = NotificationEvent.builder()
//                .channel("EMAIL")
//                .build();
//        kafkaTemplate.send("notification-delivery", notificationEvent);

        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.SUCCESS)
                .data(orderService.findByRestaurantId(args))
                .build();
    }

    @GetMapping("/{restaurantId}/list/{orderId}")
    public ApiResponse getOrders2(@PathVariable Long restaurantId, @PathVariable Long orderId) {
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(Constants.SUCCESS)
                .data(restaurantId + ", " + orderId)
                .build();
    }

    @GetMapping("/{orderId}")
    // manager can access (suresh)
    public Order getOrderDetails(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).get();
//        order.setOrderItems(orderItemRepository.findByOrderId(order.getId()));
        return order;
    }

//    @PostMapping
//    // authenticated users can access
//    public Order createOrder(@RequestBody Order order) {
//        orderRepository.save(order);
//        List<OrderItem> orderItems = order.getOrderItems();
//        orderItems.forEach(orderItem -> {
//            orderItem.setOrderId(order.id);
//            orderItemRepository.save(orderItem);
//        });
//        return order;
//    }
}