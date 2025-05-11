package matkan.com.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PaymentMethodDTO(
        String id,
        int discount,
        BigDecimal limit
) {
    @JsonCreator
    public PaymentMethodDTO(
            @JsonProperty("id") String id,
            @JsonProperty("discount") Integer discount,
            @JsonProperty("limit") BigDecimal limit
    ) {
        this(
                id,
                discount != null ? discount : 0,
                limit != null ? limit : BigDecimal.ZERO);
    }
}

