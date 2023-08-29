package com.busanit.repository;

import com.busanit.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRrepository extends JpaRepository<Cart, Long> {
}
