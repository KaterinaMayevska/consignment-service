package com.myshop.consignmentservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ConsignmentDto {
    private int quantity;
    private Date date;
    private Long itemId;
}
