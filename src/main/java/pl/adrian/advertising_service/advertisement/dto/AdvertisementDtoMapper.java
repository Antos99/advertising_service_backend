package pl.adrian.advertising_service.advertisement.dto;

import pl.adrian.advertising_service.advertisement.Advertisement;

import java.util.List;
import java.util.stream.Collectors;

public class AdvertisementDtoMapper {

    private AdvertisementDtoMapper(){}

    public static List<AdvertisementDto> mapAdvertisementsToDtos(List<Advertisement> advertisements){
        return advertisements.stream().map(AdvertisementDtoMapper::mapAdvertisementToDto).
                collect(Collectors.toList());
    }

    public static AdvertisementDto mapAdvertisementToDto(Advertisement advertisement) {
        return new AdvertisementDto(advertisement);
    }
}
