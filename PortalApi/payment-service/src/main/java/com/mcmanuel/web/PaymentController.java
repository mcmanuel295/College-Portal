package com.mcmanuel.web;

import com.mcmanuel.domain.PaymentRequest;
import com.mcmanuel.domain.PaymentResponse;
import com.mcmanuel.domain.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<PaymentResponse> payment(PaymentRequest paymentRequest){
        try{
            PaymentResponse response = paymentService.payment(paymentRequest);

            return ResponseEntity.ok(response);
        } catch( Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
