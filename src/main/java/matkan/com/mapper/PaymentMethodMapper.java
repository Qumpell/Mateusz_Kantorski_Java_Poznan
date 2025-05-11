package matkan.com.mapper;

import matkan.com.dto.PaymentMethodDTO;
import matkan.com.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PaymentMethodMapper {
    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "discount", target = "discount")
    @Mapping(source = "limit", target = "limit")
    PaymentMethod toPaymentMethod(PaymentMethodDTO paymentMethodDTO);
}
