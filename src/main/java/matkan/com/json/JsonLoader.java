package matkan.com.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import matkan.com.dto.OrderDTO;
import matkan.com.dto.PaymentMethodDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<OrderDTO> loadOrdersDTO(String path) {
        try {
            return mapper.readValue(new File(path), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error while loading orders: " + e.getMessage());
        }

    }

    public List<PaymentMethodDTO> loadPaymentMethodsDTO(String path) {
        try {
            return mapper.readValue(new File(path), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error while loading paymentMethods: " + e.getMessage());
        }

    }
}
