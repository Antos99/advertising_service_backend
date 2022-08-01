package pl.adrian.advertising_service.advertisement.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.adrian.advertising_service.address.dto.AddressMapper;
import pl.adrian.advertising_service.advertisement.Advertisement;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final AddressMapper addressMapper;

    public List<AdvertisementDtoResponse> mapToAdvertisementDtoResponses(List<Advertisement> advertisements){
        return advertisements.stream().map(this::mapToAdvertisementDtoResponse).toList();
    }

    public AdvertisementDtoResponse mapToAdvertisementDtoResponse(Advertisement advertisement) {
        AdvertisementDtoResponse advertisementDtoResponse = new AdvertisementDtoResponse(advertisement);
        advertisementDtoResponse.setAddress(addressMapper.mapToAddressDto(advertisement.getAddress()));
        return advertisementDtoResponse;
    }
}
