package com.javatechie.service;

import com.javatechie.entity.Order;
import com.javatechie.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessingService {

    private final OrderService orderService;

    private final InventoryService inventoryService;

    public OrderProcessingService(OrderService orderService, InventoryService inventoryService) {
        this.orderService = orderService;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public Order placeAnOrder(Order order) {
        //get the product details from inventory
        Product product = inventoryService.getProduct(order.getProductId());
        // Validate stock availability
        validateStockAvailability(order.getQuantity(), product.getStockQuantity());
        //update total price
        order.setTotalPrice(order.getQuantity() * product.getPrice());
        //save the order
        orderService.saveOrder(order);
        //update the stock value
        updateInventoryStock(order, product);
        return order;
    }

    private void updateInventoryStock(Order order, Product product) {
        int availableStock = product.getStockQuantity() - order.getQuantity();
        product.setStockQuantity(availableStock);
        inventoryService.updateProductDetails(product);
    }

    private void validateStockAvailability(int orderQuantity, int availableStock) {
        if (orderQuantity > availableStock) {
            throw new IllegalArgumentException("Insufficient stock!");
        }
    }

}
