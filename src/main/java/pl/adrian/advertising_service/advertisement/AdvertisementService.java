package pl.adrian.advertising_service.advertisement;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.address.AddressRepository;
import pl.adrian.advertising_service.address.AddressService;
import pl.adrian.advertising_service.address.AddressVoivodeship;
import pl.adrian.advertising_service.address.dto.AddressDto;
import pl.adrian.advertising_service.address.dto.AddressDtoMapper;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDto;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoMapper;
import pl.adrian.advertising_service.category.Category;
import pl.adrian.advertising_service.category.CategoryRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;

    public List<AdvertisementDto> getAdvertisements(){
        List<Advertisement> advertisements = advertisementRepository.findAll();
        return getAdvertisementDtosWithAddress(advertisements);
    }

    private List<AdvertisementDto> getAdvertisementDtosWithAddress(List<Advertisement> advertisements){
        List<AdvertisementDto> advertisementDtos = AdvertisementDtoMapper.mapAdvertisementsToDtos(advertisements);
        List<Long> advertisementDtoIds = advertisementDtos.stream().map(AdvertisementDto::getId).toList();
        List<AddressDto> addressDtos = addressService.getAddressesByIds(advertisementDtoIds);

        addressDtos.forEach(addressDto -> setAddressDtoForAdvertisementDtos(addressDto, advertisementDtos));

        return advertisementDtos;
    }

    private void setAddressDtoForAdvertisementDtos(AddressDto addressDto, List<AdvertisementDto> advertisementDtos) {
        advertisementDtos.stream().
                filter(advertisementDto -> advertisementDto.getId().equals(addressDto.getAdvertisementId())).
                findFirst().get().setAddress(addressDto);
    }

    public AdvertisementDto getAdvertisement(Long id) {
        AdvertisementDto advertisementDto = AdvertisementDtoMapper.
                mapAdvertisementToDto(advertisementRepository.findById(id).orElseThrow());
        advertisementDto.setAddress(addressService.getAddress(advertisementDto.getId()));

        return advertisementDto;
    }

    public List<AdvertisementDto> getAdvertisementsByFilter(Long id, Integer pageNumber, Integer pageSize,
                                                            Sort.Direction direction, String property){
         List<Advertisement> advertisements = advertisementRepository.
                 findAdvertisementsByCategoryId(id,
                         PageRequest.of(pageNumber, pageSize, Sort.by(direction, property)));

        return getAdvertisementDtosWithAddress(advertisements);
    }

    @Transactional
    public AdvertisementDto addAdvertisement(AdvertisementDto advertisementDto) {
        if (advertisementDto.getCategoryId() == null){
            throw new IllegalArgumentException("categoryId cannot be null");
        }
        if (advertisementDto.getAddress() == null){
            throw new IllegalArgumentException("address cannot be null");
        }
        Category category = categoryRepository.findById(advertisementDto.getCategoryId()).
                orElseThrow(() -> new IllegalArgumentException("Category with id " + advertisementDto.getCategoryId()
                + " does not exist"));
        String name = advertisementDto.getName();
        Float price = advertisementDto.getPrice();
        String description = advertisementDto.getDescription();
        Integer duration = advertisementDto.getDuration();
        Advertisement advertisement = new Advertisement(category, name, price, description, duration);
        advertisementRepository.save(advertisement);

        AddressDto addressDto = addressService.addAddress(advertisementDto.getAddress(), advertisement);

        AdvertisementDto advertisementDtoResult = AdvertisementDtoMapper.mapAdvertisementToDto(advertisement);
        advertisementDtoResult.setAddress(addressDto);
        return advertisementDtoResult;
    }

    @Transactional
    public AdvertisementDto editAdvertisement(AdvertisementDto advertisementDto) {
        if (advertisementDto.getAddress() == null){
            throw new IllegalArgumentException("address cannot be null");
        }
        Advertisement advertisementEdited = advertisementRepository.findById(advertisementDto.getId()).
                orElseThrow(() -> new IllegalArgumentException("Advertisement with id " + advertisementDto.getId()
                        + " does not exist"));
        advertisementEdited.setCategory(categoryRepository.findById(advertisementDto.getCategoryId()).
                orElseThrow(() -> new IllegalArgumentException("Category with id " + advertisementDto.getCategoryId()
                        + " does not exist")));
        advertisementEdited.setName(advertisementDto.getName());
        advertisementEdited.setPrice(advertisementDto.getPrice());
        advertisementEdited.setDescription(advertisementDto.getDescription());
        advertisementEdited.setModified(LocalDateTime.now());

        AddressDto addressDto = addressService.editAddress(advertisementDto.getAddress());

        AdvertisementDto advertisementDtoResult = AdvertisementDtoMapper.mapAdvertisementToDto(advertisementEdited);
        advertisementDtoResult.setAddress(addressDto);
        return advertisementDtoResult;
    }

    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }
}
