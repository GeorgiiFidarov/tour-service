package com.fidarov.tourservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TourRequest {

    private String name;
    private String description;
    private BigDecimal price;
}
