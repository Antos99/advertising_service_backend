package pl.adrian.advertising_service.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.adrian.advertising_service.address.AddressService;
import pl.adrian.advertising_service.address.dto.AddressDtoRequest;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoPostRequest;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementMapper;
import pl.adrian.advertising_service.category.CategoryRepository;
import pl.adrian.advertising_service.exceptions.BadRequestException;
import pl.adrian.advertising_service.role.EnumRole;
import pl.adrian.advertising_service.security.CustomUserDetails;
import pl.adrian.advertising_service.user.UserRepository;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {

    private AdvertisementService advertisementService;
    @Mock
    private AdvertisementRepository advertisementRepository;
    @Mock
    private AddressService addressService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AdvertisementMapper advertisementMapper;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        advertisementService = new AdvertisementService(
                advertisementRepository, addressService, categoryRepository, advertisementMapper, userRepository);
    }

    @Test
    public void shouldFindAllAdvertisements(){
        //given
        int pageNumber = 0;
        int pageSize = 5;
        Sort.Direction direction = Sort.Direction.ASC;
        String property = "id";

        //when
        advertisementService.getAdvertisements(pageNumber, pageSize, direction, property);

        //then
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        Mockito.verify(advertisementRepository).findAllAdvertisements(pageableArgumentCaptor.capture());
        Pageable capturedPageable = pageableArgumentCaptor.getValue();
        Pageable expectedPageable = PageRequest.of(pageNumber, pageSize, direction, property);
        assertThat(capturedPageable).isEqualTo(expectedPageable);
    }

    @Test
    public void methodAddAdvertisementShouldThrowBadRequestException(){
        //given
        AdvertisementDtoPostRequest advertisementDtoPostRequest = new AdvertisementDtoPostRequest(
                "sport",
                "ball",
                199.99F,
                "Very nice ball",
                new AddressDtoRequest("wielkopolskie", "Poznań", "60-681", "Widok 9/8"),
                20);
        CustomUserDetails userDetails = new CustomUserDetails(
                1L,
                "johnny",
                "HashPassword",
                Set.of(new SimpleGrantedAuthority(EnumRole.ROLE_USER.name())));
        given(categoryRepository.findByName(advertisementDtoPostRequest.getCategoryName())).willReturn(
                Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> advertisementService.addAdvertisement(advertisementDtoPostRequest, userDetails))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Wrong category name");
    }
}
