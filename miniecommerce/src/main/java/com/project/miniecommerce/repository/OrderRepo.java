package com.project.miniecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.miniecommerce.entity.CustomerOrder;

public interface OrderRepo extends JpaRepository<CustomerOrder, String>{

    List<CustomerOrder> findByUserId(String userId, Pageable pageable);

    @Query("SELECT p FROM CustomerOrder p WHERE LOWER (p.number) LIKE %:filterText% OR LOWER (p.user.name) LIKE %:filterText% ")
    List<CustomerOrder> search(@Param("filterText") String filterText, Pageable pageable);
    
}
