package matkan.com.util;

import matkan.com.model.PaymentMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentsUtils {

    public static boolean isAmountOverLimit(BigDecimal amount, BigDecimal limit) {
        return amount.compareTo(limit) > 0;
    }

    public static  BigDecimal calculateRebate(BigDecimal amountToBePaid, int discount) {
        BigDecimal discountPercentage = BigDecimal.valueOf(discount).multiply(BigDecimal.valueOf(0.01));
        return amountToBePaid.multiply(discountPercentage).setScale(2, RoundingMode.HALF_UP);
    }

    public static  BigDecimal calculateValueSpent(PaymentMethod method, BigDecimal amountToBePaid) {
        BigDecimal rebate = calculateRebate(amountToBePaid, method.getDiscount());
        return amountToBePaid.subtract(rebate).setScale(2, RoundingMode.HALF_UP);
    }
}
