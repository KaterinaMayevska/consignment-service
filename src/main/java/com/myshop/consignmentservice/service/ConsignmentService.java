package com.myshop.consignmentservice.service;

import com.myshop.consignmentservice.api.dto.ItemDto;
import com.myshop.consignmentservice.model.Consignment;
import java.util.Date;
import java.util.List;


public interface ConsignmentService {

    public Consignment add(Long itemId, int quantity, Date date) throws IllegalArgumentException;

    public void deleteById(Long id);

    public Consignment findById(Long id)throws IllegalArgumentException;

    public void update(Long id, Long itemId, int quantity, Date date) throws IllegalArgumentException;

    public List<Consignment> findAll() ;

    public void delete(Long id) throws IllegalArgumentException;

    public ItemDto getItemByConsignment (long id) throws IllegalArgumentException;

}
