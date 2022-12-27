package pl.adrian.advertising_service.advertisement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.address.AddressService;
import pl.adrian.advertising_service.address.AddressVoivodeship;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoPostRequest;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoPutRequest;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoResponse;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementMapper;
import pl.adrian.advertising_service.category.CategoryRepository;
import pl.adrian.advertising_service.exceptions.BadRequestException;
import pl.adrian.advertising_service.exceptions.ForbiddenException;
import pl.adrian.advertising_service.exceptions.NotFoundException;
import pl.adrian.advertising_service.role.EnumRole;
import pl.adrian.advertising_service.security.CustomUserDetails;
import pl.adrian.advertising_service.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final AddressService addressService;
    private final CategoryRepository categoryRepository;
    private final AdvertisementMapper advertisementMapper;
    private final UserRepository userRepository;

    public List<AdvertisementDtoResponse> getAdvertisements
            (Integer pageNumber, Integer pageSize, Sort.Direction direction, String property){
        List<Advertisement> advertisements = advertisementRepository
                .findAllAdvertisements(PageRequest.of(pageNumber, pageSize, Sort.by(direction, property)));
        return advertisementMapper.mapToAdvertisementDtoResponses(advertisements);
    }


    public AdvertisementDtoResponse getAdvertisement(Long id) throws NotFoundException {
        Advertisement advertisement = advertisementRepository.findAdvertisementsById(id).orElseThrow(
                () -> new NotFoundException("Advertisement with id: " + id + " does not exist")
        );
        return advertisementMapper.mapToAdvertisementDtoResponse(advertisement);
    }

    public List<AdvertisementDtoResponse> getAdvertisementsByCategoryName(
            String categoryName, Integer pageNumber, Integer pageSize, Sort.Direction direction, String property)
            throws BadRequestException {
        categoryRepository.findByName(categoryName).orElseThrow(
                () -> new BadRequestException("Category with name: " + categoryName + " does not exist"));

         List<Advertisement> advertisements = advertisementRepository.
                 findAdvertisementsByCategoryName(categoryName,
                         PageRequest.of(pageNumber, pageSize, Sort.by(direction, property)));

        return advertisementMapper.mapToAdvertisementDtoResponses(advertisements);
    }

    public List<AdvertisementDtoResponse> getAdvertisementsByUsername(
            String username, Integer pageNumber, Integer pageSize, Sort.Direction direction, String property)
            throws BadRequestException {
        userRepository.findUserByUsername(username).orElseThrow(
                () -> new BadRequestException("User with username: " + username + " does not exist"));

        List<Advertisement> advertisements = advertisementRepository.
                findAdvertisementsByUsername(username,
                        PageRequest.of(pageNumber, pageSize, Sort.by(direction, property)));

        return advertisementMapper.mapToAdvertisementDtoResponses(advertisements);
    }

    public AdvertisementDtoResponse addAdvertisement(
            AdvertisementDtoPostRequest advertisementDtoPostRequest,
            CustomUserDetails userDetails)
            throws BadRequestException {

        Advertisement advertisement = new Advertisement(
                categoryRepository.findByName(advertisementDtoPostRequest.getCategoryName()).orElseThrow(
                        () -> new BadRequestException("Wrong category name")),
                userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(
                        () -> new BadRequestException("Wrong username")),
                advertisementDtoPostRequest.getName(),
                advertisementDtoPostRequest.getPrice(),
                advertisementDtoPostRequest.getDescription(),
                advertisementDtoPostRequest.getDuration()
        );

        Address address = new Address(
                advertisement,
                AddressVoivodeship.getByValue(advertisementDtoPostRequest.getAddress().getVoivodeship()),
                advertisementDtoPostRequest.getAddress().getCity(),
                advertisementDtoPostRequest.getAddress().getPostCode(),
                advertisementDtoPostRequest.getAddress().getStreet()
        );

        advertisement.setAddress(address);

        return advertisementMapper.mapToAdvertisementDtoResponse(advertisementRepository.save(advertisement));
    }

    public AdvertisementDtoResponse editAdvertisement(
            AdvertisementDtoPutRequest advertisementDto,
            Long id,
            CustomUserDetails userDetails)
            throws NotFoundException, ForbiddenException {

        Advertisement advertisementEdited = advertisementRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Advertisement with id: " + id + " does not exist")
        );
        if (!advertisementEdited.getUser().getUsername().equals(userDetails.getUsername()) &&
                !userDetails.getAuthorities().contains(new SimpleGrantedAuthority(EnumRole.ROLE_MODERATOR.name())))
            throw new ForbiddenException(
                    "User with username: " + userDetails.getUsername() +
                            " does not have access to edit advertisement with id: " + advertisementEdited.getId()
            );
        advertisementEdited.setCategory(
                categoryRepository.findByName(advertisementDto.getCategoryName()).orElseThrow(
                        () -> new NotFoundException(
                                "Category with name: " + advertisementDto.getCategoryName() + " does not exist"
                        )));

        advertisementEdited.setName(advertisementDto.getName());
        advertisementEdited.setPrice(advertisementDto.getPrice());
        advertisementEdited.setDescription(advertisementDto.getDescription());
        advertisementEdited.setModified(LocalDateTime.now());

        addressService.editAddress(advertisementDto.getAddress(), id);

        return advertisementMapper.mapToAdvertisementDtoResponse(advertisementEdited);
    }


    public void deleteAdvertisement(Long id, CustomUserDetails userDetails) throws NotFoundException, ForbiddenException {
        Advertisement advertisement = advertisementRepository.findAdvertisementsById(id).orElseThrow(
                () -> new NotFoundException("Advertisement with id: " + id + " does not exist")
        );
        if (!advertisement.getUser().getUsername().equals(userDetails.getUsername()) &&
                !userDetails.getAuthorities().contains(new SimpleGrantedAuthority(EnumRole.ROLE_MODERATOR.name())))
            throw new ForbiddenException(
                    "User with username: " + userDetails.getUsername() +
                            " does not have access to delete advertisement with id: " + advertisement.getId()
            );
        advertisementRepository.deleteById(id);
    }
}
