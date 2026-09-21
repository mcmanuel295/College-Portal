package com.mcmanuel.domain;

import lombok.Data;

@Data
public class PaymentRequest {
    String currency;
    String itemName;
    long quantity;
    long amount ;
}
