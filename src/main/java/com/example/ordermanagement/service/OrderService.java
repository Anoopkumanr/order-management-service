package com.example.ordermanagement.service;

import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.exception.OrderNotFoundException;
import com.example.ordermanagement.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(Order order) {
        return repository.save(order);
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order update(Long id, Order request) {
        Order order = getById(id);
        order.setCustomerName(request.getCustomerName());
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setStatus(request.getStatus());
        return repository.save(order);
    }

    public Order patch(Long id, Order request) {
        Order order = getById(id);

        if (request.getCustomerName() != null) order.setCustomerName(request.getCustomerName());
        if (request.getProductName() != null) order.setProductName(request.getProductName());
        if (request.getQuantity() != null) order.setQuantity(request.getQuantity());
        if (request.getPrice() != null) order.setPrice(request.getPrice());
        if (request.getStatus() != null) order.setStatus(request.getStatus());

        return repository.save(order);
    }

    public void delete(Long id) {
        Order order = getById(id);
        repository.delete(order);
    }
}
