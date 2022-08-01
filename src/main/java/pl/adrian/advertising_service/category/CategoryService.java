package pl.adrian.advertising_service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.advertisement.AdvertisementRepository;
import pl.adrian.advertising_service.category.dto.CategoryDtoResponse;
import pl.adrian.advertising_service.category.dto.CategoryMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CategoryMapper categoryMapper;


    public List<CategoryDtoResponse> getCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.mapToCategoryDtoResponses(categories);
    }

    public CategoryDtoResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return categoryMapper.mapToCategoryDtoResponse(category);
    }

    public CategoryDtoResponse addCategory(Category category) {
        return categoryMapper.mapToCategoryDtoResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDtoResponse editCategory(Category category) {
        Category categoryEdited = categoryRepository.findById(category.getId()).orElseThrow();
        categoryEdited.setName(category.getName());
        return categoryMapper.mapToCategoryDtoResponse(categoryEdited);
    }

    @Transactional
    public void deleteCategory(Long id) {
        advertisementRepository.setNullForAdvertisementsCategoryIdByCategoryId(id);
        categoryRepository.deleteById(id);
    }
}
