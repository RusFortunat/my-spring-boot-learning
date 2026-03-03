package com.appsdeveloperblog.ws.products.repository;

import com.appsdeveloperblog.ws.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
