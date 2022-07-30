package pl.adrian.advertising_service.category;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.advertisement.Advertisement;
import pl.adrian.advertising_service.advertisement.AdvertisementRepository;
import pl.adrian.advertising_service.category.dto.CategoryDto;
import pl.adrian.advertising_service.category.dto.CategoryDtoMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdvertisementRepository advertisementRepository;


    public List<CategoryDto> getCategories(){
        return CategoryDtoMapper.mapToCategoriesDtos(categoryRepository.findAll());
    }

    public CategoryDto getCategory(Long id) {
        return CategoryDtoMapper.mapToCategoryDto(categoryRepository.findById(id).orElseThrow());
    }

}
