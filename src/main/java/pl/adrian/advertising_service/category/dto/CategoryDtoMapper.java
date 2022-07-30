package pl.adrian.advertising_service.category.dto;

import pl.adrian.advertising_service.category.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDtoMapper {
    private CategoryDtoMapper(){}

    public static List<CategoryDto> mapToCategoriesDtos(List<Category> categories) {
        return categories.stream().map(CategoryDtoMapper::mapToCategoryDto).collect(Collectors.toList());
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category);
    }
}
