package com.myshop.consignmentservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Data
@NoArgsConstructor
@Table(name = "consignments")
public final class Consignment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="quantity")
    private int quantity;
    @Column(name="date")
    private Date date;
    @Column(name = "item_id")
    private Long itemId;

    public Consignment(int quantity, Date date, Long itemId) {
        this.quantity = quantity;
        this.date = date;
        this.itemId = itemId;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

}
