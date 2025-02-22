package org.nexus.nexkartfrontend.checkout.paypal;

public class PayPalApiException extends RuntimeException {
    public PayPalApiException(String message) {
        super(message);
    }
}
