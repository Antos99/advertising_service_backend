package pl.adrian.advertising_service.address.dto;

import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.advertisement.Advertisement;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDto;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AddressDtoMapper {
    private AddressDtoMapper(){}

    public static List<AddressDto> mapAddressesToDtos(List<Address> addresses){
        return addresses.stream().map(AddressDtoMapper::mapAddressToDto).
                collect(Collectors.toList());
    }

    public static AddressDto mapAddressToDto(Address address) {
        return new AddressDto(address);
    }
}
