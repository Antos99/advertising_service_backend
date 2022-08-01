package pl.adrian.advertising_service.address.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.adrian.advertising_service.address.Address;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AddressMapper {


    public List<AddressDto> mapToAddressDtos(List<Address> addresses){
        return addresses.stream().map(this::mapToAddressDto).collect(Collectors.toList());
    }

    public AddressDto mapToAddressDto(Address address) {
        return new AddressDto(address);
    }
}
