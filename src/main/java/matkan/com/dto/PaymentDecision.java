package matkan.com.dto;

import java.math.BigDecimal;

public record PaymentDecision(
        String paymentMethodId,
//        double amountSpent
        BigDecimal amount
) {
}
