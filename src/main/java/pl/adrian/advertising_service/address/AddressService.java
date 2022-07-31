package pl.adrian.advertising_service.address;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.address.dto.AddressDto;
import pl.adrian.advertising_service.address.dto.AddressDtoMapper;
import pl.adrian.advertising_service.advertisement.Advertisement;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressDto> getAddresses(){
        return AddressDtoMapper.mapAddressesToDtos(addressRepository.findAll());
    }

    public AddressDto getAddress(Long id){
        return AddressDtoMapper.mapAddressToDto(addressRepository.findById(id).orElseThrow());
    }

    public List<AddressDto> getAddressesByIds(List<Long> ids){
        List<Address> addresses = addressRepository.findAddressesByIdIn(ids);
        return AddressDtoMapper.mapAddressesToDtos(addresses);
    }

    public AddressDto addAddress(AddressDto addressDto, Advertisement advertisement){
        AddressVoivodeship addressVoivodeship = AddressVoivodeship.getByValue(
                addressDto.getVoivodeship());
        String city = addressDto.getCity();
        String postCode = addressDto.getPostCode();
        String street = addressDto.getStreet();
        return AddressDtoMapper.mapAddressToDto(addressRepository.save(
                new Address(advertisement,addressVoivodeship,city,postCode,street)));
    }

    public AddressDto editAddress(AddressDto addressDto){
        Address address = addressRepository.findById(addressDto.getAdvertisementId()).orElseThrow();
        AddressVoivodeship addressVoivodeship = AddressVoivodeship.getByValue(addressDto.getVoivodeship());
        address.setVoivodeship(addressVoivodeship);
        address.setCity(addressDto.getCity());
        address.setPostCode(addressDto.getPostCode());
        address.setStreet(addressDto.getStreet());
        return AddressDtoMapper.mapAddressToDto(address);
    }

}
