package com.mcmanuel.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    String name;
    long amount;
    String url;
    String id;

}
