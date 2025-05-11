package matkan.com.core;

import matkan.com.dto.OrderDTO;
import matkan.com.dto.PaymentDecision;
import matkan.com.dto.PaymentMethodDTO;
import matkan.com.mapper.OrderMapper;
import matkan.com.mapper.PaymentMethodMapper;
import matkan.com.model.Order;
import matkan.com.model.PaymentMethod;
import matkan.com.json.JsonLoader;
import matkan.com.service.PaymentOptimizer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationRunner {

    private final JsonLoader jsonLoader = new JsonLoader();
    private final PaymentOptimizer paymentOptimizer = new PaymentOptimizer();

    public void run(String ordersPath, String paymentsPath) {
        List<OrderDTO> orderDTOS = jsonLoader.loadOrdersDTO(ordersPath);
        List<PaymentMethodDTO> paymentMethodDTOs = jsonLoader.loadPaymentMethodsDTO(paymentsPath);

        List<Order> orders = orderDTOS.stream()
                .map(OrderMapper.INSTANCE::toOrder)
                .toList();

        List<PaymentMethod> paymentMethods = paymentMethodDTOs.stream()
                .map(PaymentMethodMapper.INSTANCE::toPaymentMethod)
                .toList();

        List<PaymentDecision> decisions = paymentOptimizer.optimizePayments(orders, paymentMethods);

        Map<String, BigDecimal> totalByMethod = decisions.stream()
                .collect(Collectors.groupingBy(
                        PaymentDecision::paymentMethodId,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                PaymentDecision::amount,
                                BigDecimal::add
                        )
                ));

        totalByMethod.forEach((method, total) ->
                System.out.println(method + " " + total)
        );
    }
}
