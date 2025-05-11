package matkan.com.service;

import matkan.com.dto.PaymentDecision;
import matkan.com.model.Order;
import matkan.com.model.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentOptimizerTest {

    @Test
    public void testSingleOrderWithDiscountMethod() {
        // given
        PaymentOptimizer optimizer = new PaymentOptimizer();

        Order order = new Order("ORDER 1", BigDecimal.valueOf(100), List.of("CARD", "PUNKTY"));
        PaymentMethod card = new PaymentMethod("CARD", 20, BigDecimal.valueOf(200));
        PaymentMethod points = new PaymentMethod("PUNKTY", 0, BigDecimal.valueOf(100));

        // when
        List<PaymentDecision> result = optimizer.optimizePayments(List.of(order), List.of(card, points));

        // then
        assertEquals(1, result.size());
        assertEquals("CARD", result.get(0).paymentMethodId());

        BigDecimal expected = BigDecimal.valueOf(80.00);
        BigDecimal actual = result.get(0).amount();
        assertTrue(expected.compareTo(actual) == 0, "Expected: " + expected + ", but got: " + actual);
    }


    @Test
    public void testMixedPaymentWithPointsAndCard() {
        // given
        PaymentOptimizer optimizer = new PaymentOptimizer();

        Order order = new Order("ORDER 1", BigDecimal.valueOf(100), List.of("CARD"));
        PaymentMethod points = new PaymentMethod("PUNKTY", 15, BigDecimal.valueOf(30));
        PaymentMethod card = new PaymentMethod("CARD", 5, BigDecimal.valueOf(200));

        // when
        List<PaymentDecision> result = optimizer.optimizePayments(List.of(order), List.of(points, card));

        // then
        assertEquals(2, result.size());

        PaymentDecision decision1 = result.get(0);
        assertEquals("PUNKTY", decision1.paymentMethodId());
        BigDecimal expected1 = BigDecimal.valueOf(30.00);
        assertTrue(expected1.compareTo(decision1.amount()) == 0, "Expected: " + expected1 + ", but got: " + decision1.amount());

        PaymentDecision decision2 = result.get(1);
        assertEquals("CARD", decision2.paymentMethodId());
        BigDecimal expected2 = BigDecimal.valueOf(60);
        assertTrue(expected2.compareTo(decision2.amount()) == 0, "Expected: " + expected2 + ", but got: " + decision2.amount());
    }

    @Test
    public void testNoAvailablePaymentMethodDueToLimit() {
        //given
        PaymentOptimizer optimizer = new PaymentOptimizer();

        Order order = new Order("ORDER 1", BigDecimal.valueOf(100), List.of("CARD"));
        PaymentMethod card = new PaymentMethod("CARD", 10, BigDecimal.valueOf(50));
        PaymentMethod points = new PaymentMethod("PUNKTY", 0, BigDecimal.valueOf(5));

        //when
        List<PaymentDecision> result = optimizer.optimizePayments(List.of(order), List.of(card, points));

        //then
        assertEquals(0, result.size());
    }

    @Test
    public void testOrderWithoutPromotions() {
        //given
        PaymentOptimizer optimizer = new PaymentOptimizer();

        Order order = new Order("ORDER 1", BigDecimal.valueOf(100), List.of());
        PaymentMethod method = new PaymentMethod("BLIK", 5, BigDecimal.valueOf(150));
        PaymentMethod points = new PaymentMethod("PUNKTY", 0, BigDecimal.valueOf(0));

        //when
        List<PaymentDecision> result = optimizer.optimizePayments(List.of(order), List.of(method, points));

        //then
        assertEquals(1, result.size());
        assertEquals("BLIK", result.get(0).paymentMethodId());
    }

    @Test
    public void testMultipleOrdersLimitsUpdate() {
        //given
        PaymentOptimizer optimizer = new PaymentOptimizer();


        Order order1 = new Order("ORDER 1", BigDecimal.valueOf(100), List.of("CARD"));
        Order order2 = new Order("ORDER 2", BigDecimal.valueOf(100), List.of("CARD"));
        PaymentMethod card = new PaymentMethod("CARD", 10, BigDecimal.valueOf(150)); // starczy tylko na jedno z rabatem
        PaymentMethod points = new PaymentMethod("PUNKTY", 0, BigDecimal.valueOf(0)); // nieużywane

        //when
        List<PaymentDecision> result = optimizer.optimizePayments(List.of(order1, order2), List.of(card, points));

        //then
        assertEquals(1, result.size());
        assertEquals("CARD", result.get(0).paymentMethodId());


        BigDecimal totalSpent = result.stream()
                .map(PaymentDecision::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertTrue(totalSpent.compareTo(BigDecimal.valueOf(200)) <= 0);
    }

    @Test
    public void testOnlyPointsPayment() {
        //given
        PaymentOptimizer optimizer = new PaymentOptimizer();

        Order order = new Order("ORDER 1", BigDecimal.valueOf(50), List.of());
        PaymentMethod points = new PaymentMethod("PUNKTY", 15, BigDecimal.valueOf(60));

        //when
        List<PaymentDecision> result = optimizer.optimizePayments(List.of(order), List.of(points));

        //then
        assertEquals(1, result.size());
        assertEquals("PUNKTY", result.get(0).paymentMethodId());
        assertEquals(0, BigDecimal.valueOf(42.5).compareTo(result.get(0).amount()));
    }
}