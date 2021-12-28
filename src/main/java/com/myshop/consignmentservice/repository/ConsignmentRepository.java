package com.myshop.consignmentservice.repository;

import com.myshop.consignmentservice.model.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsignmentRepository extends JpaRepository<Consignment, Long> {
    List<Consignment> findAllByItemId(Long itemId);
}
