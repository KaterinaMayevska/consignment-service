package com.myshop.consignmentservice.api;

import com.myshop.consignmentservice.api.dto.ConsignmentDto;
import com.myshop.consignmentservice.api.dto.ItemDto;
import com.myshop.consignmentservice.model.Consignment;
import com.myshop.consignmentservice.service.ConsignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consignments")
public class ConsignmentController {
    private final ConsignmentService consignmentService;


    @GetMapping
    public ResponseEntity<List<Consignment>> showAll() {
        final List<Consignment> consignments = consignmentService.findAll();
        return ResponseEntity.ok(consignments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consignment> showById(@PathVariable long id) {
        try {
            final Consignment consignment = consignmentService.findById(id);

            return ResponseEntity.ok(consignment);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/item")
    public ResponseEntity<ItemDto> getItemByConsignment(@PathVariable long id) {
        try {
            final ItemDto itemDto = consignmentService.getItemByConsignment(id);
            return ResponseEntity.ok(itemDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ConsignmentDto consignmentDto) {
        long itemId = consignmentDto.getItemId();
        int quantity = consignmentDto.getQuantity();
        Date date = consignmentDto.getDate();

        try {
            final long consignmentId = consignmentService.add(itemId, quantity, date).getId();
            final String consUri = String.format("/consignments/%d", consignmentId);

            return ResponseEntity.created(URI.create(consUri)).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody ConsignmentDto consignmentDto) {
        long itemId = consignmentDto.getItemId();
        int quantity = consignmentDto.getQuantity();
        Date date = consignmentDto.getDate();

        try {
            consignmentService.update(id, itemId, quantity, date);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
       consignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
