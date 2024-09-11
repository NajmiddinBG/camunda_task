package uz.sqb.camunda_sqb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sqb.camunda_sqb.entity.order.Order;
import uz.sqb.camunda_sqb.repository.OrderRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }


    @GetMapping("/get-order-by-id/{order-id}")
    public ResponseEntity<Order> getByOrderId(@PathVariable("order-id") Long orderId) {
        Order byOrderId = orderRepository.findByOrderId(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        return ResponseEntity.ok(byOrderId);
    }
}
