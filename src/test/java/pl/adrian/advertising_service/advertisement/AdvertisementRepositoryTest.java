package pl.adrian.advertising_service.advertisement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import pl.adrian.advertising_service.category.Category;
import pl.adrian.advertising_service.category.CategoryRepository;
import pl.adrian.advertising_service.user.User;
import pl.adrian.advertising_service.user.UserRepository;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AdvertisementRepositoryTest {

    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
     public void setUp(){
        User user1 = new User("user1", "password", "user1@gmail.com", new HashSet<>());
        User user2 = new User("user2", "password", "user2@gmail.com", new HashSet<>());
        User user3 = new User("user3", "password", "user3@gmail.com", new HashSet<>());
        List<User> users = List.of(user1, user2, user3);
        userRepository.saveAll(users);

        Category category1 = new Category("sport");
        Category category2 = new Category("music");
        Category category3 = new Category("automotive");
        List<Category> categories = List.of(category1, category2, category3);
        categoryRepository.saveAll(categories);
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnAdvertisementsOfGivenUser(){
        //given
        User user1 = userRepository.findUserByUsername("user1").orElseThrow();
        User user2 = userRepository.findUserByUsername("user2").orElseThrow();
        Category category = categoryRepository.findByName("sport").orElseThrow();
        Advertisement ad1 = new Advertisement(category, user1, "ad1", 19.99F, "ad1 description", 10);
        Advertisement ad2 = new Advertisement(category, user1, "ad2", 19.99F, "ad2 description", 10);
        Advertisement ad3 = new Advertisement(category, user2, "ad3", 19.99F, "ad3 description", 10);
        List<Advertisement> adsToSave = List.of(ad1, ad2, ad3);
        advertisementRepository.saveAll(adsToSave);
        Pageable pageable = PageRequest.of(0, 10);

        //when
        List<Advertisement> ads = advertisementRepository.findAdvertisementsByUsername(user1.getUsername(), pageable);

        //then
        assertThat(ads.size()).isEqualTo(2);
        for (Advertisement ad:ads){
            assertThat(ad.getUser().getUsername()).isEqualTo(user1.getUsername());
        }
    }

    @Test
    public void shouldReturnAdvertisementsByGivenCategory(){
        //given
        User user = userRepository.findUserByUsername("user1").orElseThrow();
        Category category1 = categoryRepository.findByName("sport").orElseThrow();
        Category category2 = categoryRepository.findByName("music").orElseThrow();
        Advertisement ad1 = new Advertisement(category1, user, "ad1", 19.99F, "ad1 description", 10);
        Advertisement ad2 = new Advertisement(category1, user, "ad2", 19.99F, "ad2 description", 10);
        Advertisement ad3 = new Advertisement(category2, user, "ad3", 19.99F, "ad3 description", 10);
        List<Advertisement> adsToSave = List.of(ad1, ad2, ad3);
        advertisementRepository.saveAll(adsToSave);
        Pageable pageable = PageRequest.of(0, 10);

        //when
        List<Advertisement> ads = advertisementRepository.findAdvertisementsByCategoryName(category1.getName(), pageable);

        //then
        assertThat(ads.size()).isEqualTo(2);
        for (Advertisement ad:ads){
            assertThat(ad.getCategory().getName()).isEqualTo(category1.getName());
        }
    }

    @Test
    public void shouldReturnNumberOfAdvertisementsOfGivenCategory(){
        //given
        User user = userRepository.findUserByUsername("user1").orElseThrow();
        Category category1 = categoryRepository.findByName("sport").orElseThrow();
        Category category2 = categoryRepository.findByName("music").orElseThrow();
        Advertisement ad1 = new Advertisement(category1, user, "ad1", 19.99F, "ad1 description", 10);
        Advertisement ad2 = new Advertisement(category1, user, "ad2", 19.99F, "ad2 description", 10);
        Advertisement ad3 = new Advertisement(category2, user, "ad3", 19.99F, "ad3 description", 10);
        List<Advertisement> adsToSave = List.of(ad1, ad2, ad3);
        advertisementRepository.saveAll(adsToSave);

        //when
        Long numberOfAdvertisements = advertisementRepository.countAdvertisementsByCategoryName(category1.getName());

        //then
        assertThat(numberOfAdvertisements).isEqualTo(2L);
    }
}
