package com.javatechie.service;

import com.javatechie.entity.Product;
import com.javatechie.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {


    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    //    @Transactional(propagation=Propagation.REQUIRED)
    public Product updateProductDetails(Product product) {
        return inventoryRepository.save(product);
    }


    public Product getProduct(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Product not available with id : " + id)
                );
    }
}
