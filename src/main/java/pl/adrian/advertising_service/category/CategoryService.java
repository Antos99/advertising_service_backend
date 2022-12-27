package pl.adrian.advertising_service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.advertisement.AdvertisementRepository;
import pl.adrian.advertising_service.category.dto.CategoryDtoRequest;
import pl.adrian.advertising_service.category.dto.CategoryDtoResponse;
import pl.adrian.advertising_service.category.dto.CategoryMapper;
import pl.adrian.advertising_service.exceptions.ConflictException;
import pl.adrian.advertising_service.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CategoryMapper categoryMapper;


    public List<CategoryDtoResponse> getCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.mapToCategoryDtoResponses(categories);
    }

    public CategoryDtoResponse getCategory(String name) throws NotFoundException {
        Category category = categoryRepository.findByName(name).orElseThrow(() ->
                new NotFoundException("Category with name: " + name + " does not exist"));
        return categoryMapper.mapToCategoryDtoResponse(category);
    }

    public CategoryDtoResponse addCategory(CategoryDtoRequest categoryDtoRequest) throws ConflictException {
        if (categoryRepository.existsByName(categoryDtoRequest.getName()))
            throw new ConflictException("Category with name: " + categoryDtoRequest.getName() + " already exists");
        Category category = new Category(categoryDtoRequest.getName());
        return categoryMapper.mapToCategoryDtoResponse(categoryRepository.save(category));
    }

    public CategoryDtoResponse editCategory(String name, CategoryDtoRequest category) throws NotFoundException, ConflictException {
        Category categoryEdited = categoryRepository.findByName(name).orElseThrow(
                () -> new NotFoundException("Category with name: " + name + " does not exist")
        );
        if (categoryRepository.existsByName(category.getName()))
            throw new ConflictException("Category with name: " + category.getName() + " already exists");
        categoryEdited.setName(category.getName());
        advertisementRepository.flush();
        return categoryMapper.mapToCategoryDtoResponse(categoryEdited);
    }

    public void deleteCategory(String name) throws NotFoundException {
        categoryRepository.findByName(name).orElseThrow(
                () -> new NotFoundException("Category with name: " + name + " does not exist")
        );
        advertisementRepository.setNullForAdvertisementsCategoryNameByCategoryName(name);
        categoryRepository.deleteByName(name);
    }
}
