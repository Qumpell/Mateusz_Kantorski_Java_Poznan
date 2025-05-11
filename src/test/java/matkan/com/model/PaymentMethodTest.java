package matkan.com.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodTest {


    @Test
    void testReduceLimit_validAmount_shouldReduceLimit() {
        //given
        PaymentMethod method = new PaymentMethod("CARD", 10, BigDecimal.valueOf(100));

        //when
        method.reduceLimit(BigDecimal.valueOf(30));

        //then
        assertEquals(0, BigDecimal.valueOf(70).compareTo(method.getLimit()));
    }

    @Test
    void testReduceLimit_negativeAmount_shouldThrowException() {
        //given
        PaymentMethod method = new PaymentMethod("CARD", 10, BigDecimal.valueOf(100));

        //when then
        assertThrows(IllegalArgumentException.class, () ->
                method.reduceLimit(BigDecimal.valueOf(-10))
        );
    }

    @Test
    void testReduceLimit_overLimit_shouldThrowException() {
        //given
        PaymentMethod method = new PaymentMethod("CARD", 10, BigDecimal.valueOf(50));

        //when then
        assertThrows(IllegalStateException.class, () ->
                method.reduceLimit(BigDecimal.valueOf(60))
        );
    }
}