package matkan.com.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public record OrderDTO(
        String id,

        BigDecimal value,

        List<String> promotions

){
        @JsonCreator
        public OrderDTO(
                @JsonProperty("id") String id,
                @JsonProperty("value") BigDecimal value,
                @JsonProperty("promotions") List<String> promotions
        ) {
                this.id = id;
                this.value = value;
                this.promotions = promotions == null ? Collections.emptyList() : promotions;
        }
}
