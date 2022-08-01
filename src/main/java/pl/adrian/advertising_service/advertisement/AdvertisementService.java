package pl.adrian.advertising_service.advertisement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.address.AddressService;
import pl.adrian.advertising_service.address.AddressVoivodeship;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoRequest;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoResponse;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementMapper;
import pl.adrian.advertising_service.category.CategoryRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final AddressService addressService;
    private final CategoryRepository categoryRepository;
    private final AdvertisementMapper advertisementMapper;

    public List<AdvertisementDtoResponse> getAdvertisements(){
        List<Advertisement> advertisements = advertisementRepository.findAllAdvertisements();
        return advertisementMapper.mapToAdvertisementDtoResponses(advertisements);
    }


    public AdvertisementDtoResponse getAdvertisement(Long id) {
        Advertisement advertisement = advertisementRepository.findAdvertisementsById(id);
        return advertisementMapper.mapToAdvertisementDtoResponse(advertisement);
    }

    public List<AdvertisementDtoResponse> getAdvertisementsByFilter(
            Long id, Integer pageNumber, Integer pageSize, Sort.Direction direction, String property){

         List<Advertisement> advertisements = advertisementRepository.
                 findAdvertisementsByCategoryId(id, PageRequest.of(pageNumber, pageSize, Sort.by(direction, property)));

        return advertisementMapper.mapToAdvertisementDtoResponses(advertisements);
    }

    @Transactional
    public AdvertisementDtoResponse addAdvertisement(AdvertisementDtoRequest advertisementDtoRequest){
        if (advertisementDtoRequest.getCategoryId() == null){
            throw new IllegalArgumentException("categoryId cannot be null");
        }
        if (advertisementDtoRequest.getAddress() == null){
            throw new IllegalArgumentException("address cannot be null");
        }

        Advertisement advertisement = new Advertisement(
                categoryRepository.findById(advertisementDtoRequest.getCategoryId()).orElseThrow(),
                advertisementDtoRequest.getName(),
                advertisementDtoRequest.getPrice(),
                advertisementDtoRequest.getDescription(),
                advertisementDtoRequest.getDuration()
        );

        Address address = new Address(
                advertisement,
                AddressVoivodeship.getByValue(advertisementDtoRequest.getAddress().getVoivodeship()),
                advertisementDtoRequest.getAddress().getCity(),
                advertisementDtoRequest.getAddress().getPostCode(),
                advertisementDtoRequest.getAddress().getStreet()
        );

        advertisement.setAddress(address);

        return advertisementMapper.mapToAdvertisementDtoResponse(advertisementRepository.save(advertisement));
    }

    @Transactional
    public AdvertisementDtoResponse editAdvertisement(AdvertisementDtoRequest advertisementDtoRequest) {
        if (advertisementDtoRequest.getCategoryId() == null){
            throw new IllegalArgumentException("categoryId cannot be null");
        }
        if (advertisementDtoRequest.getAddress() == null){
            throw new IllegalArgumentException("address cannot be null");
        }

        Advertisement advertisementEdited = advertisementRepository.
                findById(advertisementDtoRequest.getId()).orElseThrow();

        advertisementEdited.setCategory(
                categoryRepository.findById(advertisementDtoRequest.getCategoryId()).orElseThrow()
        );

        advertisementEdited.setName(advertisementDtoRequest.getName());
        advertisementEdited.setPrice(advertisementDtoRequest.getPrice());
        advertisementEdited.setDescription(advertisementDtoRequest.getDescription());
        advertisementEdited.setModified(LocalDateTime.now());

        addressService.editAddress(advertisementDtoRequest.getAddress());

        return advertisementMapper.mapToAdvertisementDtoResponse(advertisementEdited);
    }


    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }
}
