package com.fidarov.tourservice.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TourResponse {
    @Id
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
}
