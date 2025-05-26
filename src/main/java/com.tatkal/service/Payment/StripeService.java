package com.tatkal.service.Payment;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tatkal.dao.PaymentDAO;
import com.tatkal.model.StripeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    @Value("${stripe.secret.key}")
    private String secretKey;
    @Value("${stripe.success-url}")
    private String successUrl;
    @Value("${stripe.cancel-url}")
    private String cancelUrl;
    public StripeResponse doPayment(PaymentDAO paymentDAO){
        Stripe.apiKey = secretKey;
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(paymentDAO.getName())
                .build();
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder().setCurrency("INR").setUnitAmount(paymentDAO.getAmount()).setProductData(productData).build();
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder().setQuantity(paymentDAO.getQuantity()).setPriceData(priceData).build();
        SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
                .addLineItem(lineItem)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .build();

        Session session = null;
        try {
            session = Session.create(params);
        } catch (Exception e) {
            System.out.println("Error creating Stripe session: " + e.getMessage());
        }

        return StripeResponse.builder().status("SUCCESS").message("Payment session created successfully")
                .sessionId(session != null ? session.getId() : null)
                .sessionUrl(session != null ? session.getUrl() : null)
                .build();
    }

    public String getPaymentStatus(String sessionId) {
        Stripe.apiKey = secretKey;
        try {
            Session session = Session.retrieve(sessionId);
            if (session != null && "paid".equals(session.getPaymentStatus())) {
                return "Payment successful";
            } else {
                return "Payment not completed or failed";
            }
        } catch (Exception e) {
            return "Error retrieving payment status: " + e.getMessage();
        }
    }
}
