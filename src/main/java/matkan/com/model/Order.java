package matkan.com.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    private String id;
    private BigDecimal value;
    private List<String> promotions  = new ArrayList<>();

}
