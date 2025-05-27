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

    /**
     * This method is called when the user clicks the "Make Payment" button.
     * It creates a Stripe Checkout session using the payment details provided.
     * The session URL is used to redirect the user to the Stripe payment page.
     * We use this session ID to poll the status in a separate function.
     */
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

    /**
     * This method polls the Stripe API using the session ID to check the payment status.
     * It is called repeatedly (with some delay) for up to 5 minutes after payment session creation.
     * If the payment status is "paid", we proceed with the booking.
     * Otherwise, after timeout or failure, the payment is considered unsuccessful.
     */
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
