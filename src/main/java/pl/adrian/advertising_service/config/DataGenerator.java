package pl.adrian.advertising_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.adrian.advertising_service.address.AddressVoivodeship;
import pl.adrian.advertising_service.address.dto.AddressDtoRequest;
import pl.adrian.advertising_service.advertisement.AdvertisementService;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoPostRequest;
import pl.adrian.advertising_service.category.CategoryService;
import pl.adrian.advertising_service.category.dto.CategoryDtoRequest;
import pl.adrian.advertising_service.exceptions.BadRequestException;
import pl.adrian.advertising_service.exceptions.ConflictException;
import pl.adrian.advertising_service.exceptions.NotFoundException;
import pl.adrian.advertising_service.role.EnumRole;
import pl.adrian.advertising_service.role.Role;
import pl.adrian.advertising_service.role.RoleService;
import pl.adrian.advertising_service.security.CustomUserDetails;
import pl.adrian.advertising_service.user.UserService;
import pl.adrian.advertising_service.user.dto.UserDtoRequest;

import java.util.ArrayList;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataGenerator {
    private final AdvertisementService advertisementService;
    private final CategoryService categoryService;
    private final UserService userService;

    public void generateData() throws BadRequestException, ConflictException, NotFoundException {
        Role userRole = new Role(EnumRole.ROLE_USER);
        Role moderatorRole = new Role(EnumRole.ROLE_MODERATOR);
        Role adminRole = new Role(EnumRole.ROLE_ADMIN);

        UserDtoRequest antonUser = new UserDtoRequest("anton", "1234", "anton@gmail.com",
                Set.of(userRole));
        UserDtoRequest bobUser = new UserDtoRequest("bob", "pass", "bob@gmail.com",
                Set.of(userRole, moderatorRole));
        UserDtoRequest denisUser = new UserDtoRequest("denis", "1234", "denis@gmail.com",
                Set.of(userRole, moderatorRole, adminRole));

        userService.addUser(antonUser);
        userService.addUser(bobUser);
        userService.addUser(denisUser);

        CategoryDtoRequest sportCategoryDtoRequest = new CategoryDtoRequest("sport");
        CategoryDtoRequest musicCategoryDtoRequest = new CategoryDtoRequest("music");
        CategoryDtoRequest automotiveCategoryDtoRequest = new CategoryDtoRequest("automotive");

        categoryService.addCategory(sportCategoryDtoRequest);
        categoryService.addCategory(musicCategoryDtoRequest);
        categoryService.addCategory(automotiveCategoryDtoRequest);

        AddressDtoRequest ad1Address = new AddressDtoRequest(
                AddressVoivodeship.WIELKOPOLSKIE.getDisplayValue(), "Poznan", "60-681", "Wodna 10");
        AdvertisementDtoPostRequest ad1 = new AdvertisementDtoPostRequest(
                "sport", "Football ball", 200.0F, "Very nice ball", ad1Address, 10);
        AddressDtoRequest ad2Address = new AddressDtoRequest(
                AddressVoivodeship.WIELKOPOLSKIE.getDisplayValue(), "Poznan", "60-681", "Rybna");
        AdvertisementDtoPostRequest ad2 = new AdvertisementDtoPostRequest(
                "sport", "weights", 150.99F, "Heavy weights", ad2Address, 20);
        AddressDtoRequest ad3Address = new AddressDtoRequest(
                AddressVoivodeship.ZACHODNIOPOMORSKIE.getDisplayValue(), "Poznan", "60-681", "Rybna");
        AdvertisementDtoPostRequest ad3 = new AdvertisementDtoPostRequest(
                "sport", "weights", 150.99F, "Heavy weights", ad3Address, 20);
        AddressDtoRequest ad4Address = new AddressDtoRequest(
                AddressVoivodeship.MAZOWIECKIE.getDisplayValue(), "Warszawa", "77-222", "Piekna 7");
        AdvertisementDtoPostRequest ad4 = new AdvertisementDtoPostRequest(
                "sport", "shoes", 99.99f, "fast shoes", ad4Address, 15);
        AddressDtoRequest ad5Address = new AddressDtoRequest(
                AddressVoivodeship.LODZKIE.getDisplayValue(), "Łódź", "80-130", "Widok");
        AdvertisementDtoPostRequest ad5 = new AdvertisementDtoPostRequest(
                "sport", "t-shirt", 49.0f, "nice t-shirt", ad5Address, 11);
        AddressDtoRequest ad6Address = new AddressDtoRequest(
                AddressVoivodeship.POMORSKIE.getDisplayValue(), "Gdańsk", "99-555", "Szybka");
        AdvertisementDtoPostRequest ad6 = new AdvertisementDtoPostRequest(
                "sport", "gloves", 20.50f, "sticky gloves", ad6Address, 5);
        AddressDtoRequest ad7Address = new AddressDtoRequest(
                AddressVoivodeship.SWIETOKRZYSKIE.getDisplayValue(), "Kielce", "69-123", "Osiedle 5");
        AdvertisementDtoPostRequest ad7 = new AdvertisementDtoPostRequest(
                "music", "CD", 15.99f, "nice music", ad7Address, 15);
        AddressDtoRequest ad8Address = new AddressDtoRequest(
                AddressVoivodeship.LUBUSKIE.getDisplayValue(), "Gorzow", "44-987", "Górska 10");
        AdvertisementDtoPostRequest ad8 = new AdvertisementDtoPostRequest(
                "music", "speaker", 1000f, "loud speaker", ad8Address, 20);
        AddressDtoRequest ad9Address = new AddressDtoRequest(
                AddressVoivodeship.LUBUSKIE.getDisplayValue(), "Zielona Góra", "99-123", "Różana 80");
        AdvertisementDtoPostRequest ad9 = new AdvertisementDtoPostRequest(
                "music", "guitar", 500.01f, "beautiful guitar", ad9Address, 22);
        AddressDtoRequest ad10Address = new AddressDtoRequest(
                AddressVoivodeship.MALOPOLSKIE.getDisplayValue(), "Kraków", "33-009", "Wawel 7");
        AdvertisementDtoPostRequest ad10 = new AdvertisementDtoPostRequest(
                "music", "CD rap", 49.99f, "gangsta rap", ad10Address, 13);
        AddressDtoRequest ad11Address = new AddressDtoRequest(
                AddressVoivodeship.ZACHODNIOPOMORSKIE.getDisplayValue(), "Barlinek", "74-320", "Widok 9");
        AdvertisementDtoPostRequest ad11 = new AdvertisementDtoPostRequest(
                "music", "CD Cptn Liptn", 1000f, "trap music", ad11Address, 20);
        AddressDtoRequest ad12Address = new AddressDtoRequest(
                AddressVoivodeship.LUBUSKIE.getDisplayValue(), "Zielona Góra", "99-123", "Różana 80");
        AdvertisementDtoPostRequest ad12 = new AdvertisementDtoPostRequest(
                "music", "piano", 1666.66f, "heavy piano", ad12Address,30);

        CustomUserDetails antonDetails = new CustomUserDetails(1L, "anton", "",
                new ArrayList<>());
        CustomUserDetails bobDetails = new CustomUserDetails(1L, "bob", "",
                new ArrayList<>());
        CustomUserDetails denisDetails = new CustomUserDetails(1L, "denis", "",
                new ArrayList<>());
        advertisementService.addAdvertisement(ad1, antonDetails);
        advertisementService.addAdvertisement(ad2, bobDetails);
        advertisementService.addAdvertisement(ad3, denisDetails);
        advertisementService.addAdvertisement(ad4, antonDetails);
        advertisementService.addAdvertisement(ad5, bobDetails);
        advertisementService.addAdvertisement(ad6, denisDetails);
        advertisementService.addAdvertisement(ad7, antonDetails);
        advertisementService.addAdvertisement(ad8, bobDetails);
        advertisementService.addAdvertisement(ad9, denisDetails);
        advertisementService.addAdvertisement(ad10, antonDetails);
        advertisementService.addAdvertisement(ad11, bobDetails);
        advertisementService.addAdvertisement(ad12, denisDetails);

    }
}
