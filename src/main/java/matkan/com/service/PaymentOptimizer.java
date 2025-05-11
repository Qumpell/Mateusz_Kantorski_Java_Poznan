package matkan.com.service;

import matkan.com.dto.PaymentDecision;
import matkan.com.model.Order;
import matkan.com.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static matkan.com.util.PaymentsUtils.calculateValueSpent;
import static matkan.com.util.PaymentsUtils.isAmountOverLimit;


public class PaymentOptimizer {

    private final String POINTS_PAYMENT_METHOD_ID = "PUNKTY";

    public List<PaymentDecision> optimizePayments(List<Order> orders, List<PaymentMethod> methods) {

        List<PaymentDecision> decisions = new ArrayList<>();
        Map<String, PaymentMethod> paymentMethods = methods.stream()
                .collect(Collectors.toMap(PaymentMethod::getId, Function.identity()));

        for (Order order : orders) {
            final BigDecimal amountToBePaid = order.getValue();
            BigDecimal lowestValueSpent = new BigDecimal(Integer.MAX_VALUE);
            String bestMethodId = null;

            final PaymentMethod pointsPayment = paymentMethods.get(POINTS_PAYMENT_METHOD_ID);
            if (!isAmountOverLimit(amountToBePaid, pointsPayment.getLimit())) {
                lowestValueSpent = calculateValueSpent(pointsPayment, amountToBePaid);
                bestMethodId = POINTS_PAYMENT_METHOD_ID;
            }


            for (String paymentMethodId : order.getPromotions()) {

                if (isAmountOverLimit(amountToBePaid, paymentMethods.get(paymentMethodId).getLimit())) {
                    continue;
                }

                BigDecimal valueSpent = calculateValueSpent(paymentMethods.get(paymentMethodId), amountToBePaid);

                if (lowestValueSpent.compareTo(valueSpent) > 0) {
                    lowestValueSpent = valueSpent;
                    bestMethodId = paymentMethodId;
                }
            }

            String secondaryMethodId;
            boolean isMixedPaymentMethod = false;
            BigDecimal pointsSpent = new BigDecimal(0);


            BigDecimal tenPercent = amountToBePaid.multiply(BigDecimal.valueOf(0.10));
            if (paymentMethods.get(POINTS_PAYMENT_METHOD_ID).getLimit().compareTo(tenPercent) >= 0) {

                BigDecimal restToBePaid = amountToBePaid.subtract(paymentMethods.get(POINTS_PAYMENT_METHOD_ID).getLimit());
                pointsSpent = paymentMethods.get(POINTS_PAYMENT_METHOD_ID).getLimit();
                restToBePaid = restToBePaid.subtract(tenPercent);

                for (PaymentMethod pm : paymentMethods.values()) {

                    if (pm.equals(paymentMethods.get(POINTS_PAYMENT_METHOD_ID))) {
                        continue;
                    }

//                    if (!isAmountOverLimit(restToBePaid, pm.getLimit()) && !Objects.equals(pm.getId(), bestMethodId)) {
                    if (!isAmountOverLimit(restToBePaid, pm.getLimit())) {
                        secondaryMethodId = pm.getId();

                        BigDecimal limit = paymentMethods.get(POINTS_PAYMENT_METHOD_ID).getLimit();
                        BigDecimal total = restToBePaid.add(limit);
                        if (total.compareTo(lowestValueSpent) < 0) {
                            lowestValueSpent = restToBePaid;
                            bestMethodId = secondaryMethodId;
                            isMixedPaymentMethod = true;
                            break;
                        }
                    }
                }
            }


            if (bestMethodId == null) {
                for (PaymentMethod pm : paymentMethods.values()) {
                    if (!isAmountOverLimit(amountToBePaid, pm.getLimit())) {
                        pm.reduceLimit(amountToBePaid);
                        decisions.add(new PaymentDecision(pm.getId(), amountToBePaid));
                        break;
                    }
                }
            }
            else {

                if (isMixedPaymentMethod) {
                    paymentMethods.get(POINTS_PAYMENT_METHOD_ID).reduceLimit(pointsSpent);
                    decisions.add(new PaymentDecision(POINTS_PAYMENT_METHOD_ID, pointsSpent));
                }
                paymentMethods.get(bestMethodId).reduceLimit(lowestValueSpent);
                decisions.add(new PaymentDecision(bestMethodId, lowestValueSpent));
            }
        }

        return decisions;

    }

}
