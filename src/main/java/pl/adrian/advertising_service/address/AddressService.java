package pl.adrian.advertising_service.address;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.address.dto.AddressDtoRequest;
import pl.adrian.advertising_service.address.dto.AddressDtoResponse;
import pl.adrian.advertising_service.address.dto.AddressMapper;
import pl.adrian.advertising_service.exceptions.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public List<AddressDtoResponse> getAddresses(
            Integer pageNumber, Integer pageSize, Sort.Direction direction, String property
    ){
        List<Address> addresses = addressRepository.findAllAddresses(
                PageRequest.of(pageNumber, pageSize, Sort.by(direction, property)));
        return addressMapper.mapToAddressDtos(addresses);
    }

    public AddressDtoResponse getAddress(Long id) throws NotFoundException {
        return addressMapper.mapToAddressDto(addressRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Address with id: " + id + " does not exist")
        ));
    }

    public AddressDtoResponse editAddress(AddressDtoRequest addressDtoRequest, Long id) throws NotFoundException {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Address with id: " + id + " does not exist")
        );
        AddressVoivodeship addressVoivodeship = AddressVoivodeship.getByValue(addressDtoRequest.getVoivodeship());
        address.setVoivodeship(addressVoivodeship);
        address.setCity(addressDtoRequest.getCity());
        address.setPostCode(addressDtoRequest.getPostCode());
        address.setStreet(addressDtoRequest.getStreet());
        return addressMapper.mapToAddressDto(address);
    }
}
