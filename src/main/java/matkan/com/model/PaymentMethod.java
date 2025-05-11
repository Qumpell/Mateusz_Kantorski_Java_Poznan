package matkan.com.model;

import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentMethod {

    private String id;
    private int discount;
    private BigDecimal limit;

    public void reduceLimit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non-negative.");
        }
        if (limit.compareTo(amount) < 0) {
            throw new IllegalStateException("Not enough limit for payment method: " + id);
        }
        limit = limit.subtract(amount);
    }


}
