package pl.adrian.advertising_service.category.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.adrian.advertising_service.advertisement.AdvertisementRepository;
import pl.adrian.advertising_service.category.Category;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final AdvertisementRepository advertisementRepository;

    public List<CategoryDtoResponse> mapToCategoryDtoResponses(List<Category> categories){
        return categories.stream().map(this::mapToCategoryDtoResponse).toList();
    }

    public CategoryDtoResponse mapToCategoryDtoResponse(Category category) {
        CategoryDtoResponse categoryDtoResponse = new CategoryDtoResponse(category);
        categoryDtoResponse.setNumberOfAdvertisements(
                advertisementRepository.countAdvertisementsByCategoryName(categoryDtoResponse.getName())
        );
        return categoryDtoResponse;
    }
}
