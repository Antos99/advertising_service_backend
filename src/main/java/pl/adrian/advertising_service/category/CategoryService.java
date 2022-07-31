package pl.adrian.advertising_service.category;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.advertisement.Advertisement;
import pl.adrian.advertising_service.advertisement.AdvertisementRepository;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDto;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoMapper;
import pl.adrian.advertising_service.category.dto.CategoryDto;
import pl.adrian.advertising_service.category.dto.CategoryDtoMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdvertisementRepository advertisementRepository;


    public List<CategoryDto> getCategories(){
        List<CategoryDto> categories = CategoryDtoMapper.mapToCategoriesDtos(categoryRepository.findAll());
        categories.forEach(categoryDto -> categoryDto.
                        setNumberOfAdvertisements(
                                advertisementRepository.countAdvertisementsByCategoryId(categoryDto.getId())
                        ));
        return categories;
    }

    public CategoryDto getCategory(Long id) {
        CategoryDto categoryDto = CategoryDtoMapper.mapToCategoryDto(categoryRepository.findById(id).orElseThrow());
        categoryDto.setNumberOfAdvertisements(advertisementRepository.
                countAdvertisementsByCategoryId(categoryDto.getId()));
        return categoryDto;
    }

    public CategoryDto addCategory(Category category) {
        return CategoryDtoMapper.mapToCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto editCategory(Category category) {
        Category categoryEdited = categoryRepository.findById(category.getId()).orElseThrow();
        categoryEdited.setName(category.getName());
        CategoryDto categoryDto = CategoryDtoMapper.mapToCategoryDto(categoryEdited);
        categoryDto.setNumberOfAdvertisements(advertisementRepository.
                countAdvertisementsByCategoryId(categoryDto.getId()));
        return categoryDto;
    }

    @Transactional
    public void deleteCategory(Long id) {
        List<Advertisement> advertisements = advertisementRepository.findAdvertisementsByCategoryId(id);
        advertisements.forEach(advertisement -> advertisement.setCategory(null));
        categoryRepository.deleteById(id);
    }
}
