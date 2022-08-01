package pl.adrian.advertising_service.address;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.address.dto.AddressDto;
import pl.adrian.advertising_service.address.dto.AddressMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public List<AddressDto> getAddresses(){
        return addressMapper.mapToAddressDtos(addressRepository.findAll());
    }

    public AddressDto getAddress(Long id){
        return addressMapper.mapToAddressDto(addressRepository.findById(id).orElseThrow());
    }

    public AddressDto editAddress(AddressDto addressDto){
        Address address = addressRepository.findById(addressDto.getAdvertisementId()).orElseThrow();
        AddressVoivodeship addressVoivodeship = AddressVoivodeship.getByValue(addressDto.getVoivodeship());
        address.setVoivodeship(addressVoivodeship);
        address.setCity(addressDto.getCity());
        address.setPostCode(addressDto.getPostCode());
        address.setStreet(addressDto.getStreet());
        return addressMapper.mapToAddressDto(address);
    }
}
