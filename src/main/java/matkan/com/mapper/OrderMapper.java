package matkan.com.mapper;

import matkan.com.dto.OrderDTO;
import matkan.com.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "promotions", target = "promotions")
    Order toOrder(OrderDTO dto);
}
