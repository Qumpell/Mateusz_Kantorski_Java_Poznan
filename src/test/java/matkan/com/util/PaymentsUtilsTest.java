package matkan.com.util;

import matkan.com.model.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentsUtilsTest {

    @Test
    public void testCalculateRebate() {
        //given
        BigDecimal amount = BigDecimal.valueOf(100.00);
        int discount = 10;

        //when
        BigDecimal expectedRebate = BigDecimal.valueOf(10.00);
        BigDecimal actualRebate = PaymentsUtils.calculateRebate(amount, discount);

        //then
        assertEquals(0, expectedRebate.compareTo(actualRebate));
    }

    @Test
    public void testCalculateValueSpent() {
        //given
        PaymentMethod method = new PaymentMethod("TestMethod", 10, BigDecimal.valueOf(200));
        BigDecimal amount = BigDecimal.valueOf(100.00);

        //when
        BigDecimal expectedValueSpent = BigDecimal.valueOf(90.00);
        BigDecimal actualValueSpent = PaymentsUtils.calculateValueSpent(method, amount);

        //then
        assertEquals(0, expectedValueSpent.compareTo(actualValueSpent));
    }

    @Test
    public void testIsAmountOverLimit() {
        //given
        BigDecimal amount = BigDecimal.valueOf(100.00);
        BigDecimal limit = BigDecimal.valueOf(120.00);

        //when then
        assertFalse(PaymentsUtils.isAmountOverLimit(amount, limit));

        limit = BigDecimal.valueOf(80.00);
        assertTrue(PaymentsUtils.isAmountOverLimit(amount, limit));
    }

}