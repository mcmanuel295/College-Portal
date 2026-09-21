package com.mcmanuel.domain;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${STRIPE.API-KEY}")
    String stripeApiKey;

    public PaymentResponse payment(PaymentRequest stripeRequest){
        try{
            Stripe.apiKey = stripeApiKey;
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(stripeRequest.getItemName()).build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setProductData(productData)
                    .setCurrency(stripeRequest.getCurrency()==null? "USD": stripeRequest.getCurrency())
                    .setUnitAmount(stripeRequest.getAmount())
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(stripeRequest.getQuantity())
                    .setPriceData(priceData)
                    .build();

            SessionCreateParams createParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http::/localhost?successfulUrlS")
                    .addLineItem(lineItem).build();

            Session session = Session.create(createParams);

           return PaymentResponse.builder()
                    .amount(stripeRequest.getAmount())
                    .id(session.getId())
                    .name(stripeRequest.getItemName())
                    .url(session.getUrl())
                    .build();
        }
        catch (StripeException ex){
            System.out.println("Stripe exception "+ex.getMessage());
            throw new RuntimeException("Stripe Exception Error");
        }
    }
}
