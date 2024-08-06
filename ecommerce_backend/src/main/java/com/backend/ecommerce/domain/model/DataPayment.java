package com.backend.ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPayment {
    private String method;
    private String amount;
    private String currency;
    private String description;
}
