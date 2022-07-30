package pl.adrian.advertising_service.advertisement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.address.AddressRepository;
import pl.adrian.advertising_service.address.dto.AddressDto;
import pl.adrian.advertising_service.address.dto.AddressDtoMapper;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDto;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoMapper;
import pl.adrian.advertising_service.category.Category;
import pl.adrian.advertising_service.category.CategoryRepository;
import pl.adrian.advertising_service.category.dto.CategoryDto;
import pl.adrian.advertising_service.category.dto.CategoryDtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdvertisementService {
    AdvertisementRepository advertisementRepository;
    CategoryRepository categoryRepository;
    AddressRepository addressRepository;

    public List<AdvertisementDto> getAdvertisements(){
        List<Advertisement> advertisements = advertisementRepository.findAll();
        return getAdvertisementDtosWithAddress(advertisements);
    }

    private List<AdvertisementDto> getAdvertisementDtosWithAddress(List<Advertisement> advertisements){
        List<AdvertisementDto> advertisementDtos = AdvertisementDtoMapper.mapAdvertisementsToDtos(advertisements);
        List<Long> advertisementDtoIds = advertisementDtos.stream().map(AdvertisementDto::getId).toList();
        List<Address> addresses = addressRepository.findAddressesByIdIn(advertisementDtoIds);
        List<AddressDto> addressDtos = AddressDtoMapper.mapAddressesToDtos(addresses);

        for(AddressDto addressDto : addressDtos){
            advertisementDtos.stream().
                    filter(advertisementDto -> advertisementDto.getId() == addressDto.getId()).
                    findFirst().get().setAdvertisementAddress(addressDto);
        }
        return advertisementDtos;
    }

    public AdvertisementDto getAdvertisement(Long id) {
        return AdvertisementDtoMapper.mapAdvertisementToDto(advertisementRepository.findById(id).orElseThrow());
    }

    public List<AdvertisementDto> getAdvertisementsByCategoryId(Long id){
        return AdvertisementDtoMapper.mapAdvertisementsToDtos(advertisementRepository.findAdvertisementsByCategoryId(id));
    }

}
