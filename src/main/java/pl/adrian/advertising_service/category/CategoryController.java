package pl.adrian.advertising_service.category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.advertising_service.advertisement.Advertisement;
import pl.adrian.advertising_service.category.dto.CategoryDto;
import pl.adrian.advertising_service.category.dto.CategoryDtoMapper;

import java.util.List;

@RestController
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/categories/{id}")
    public CategoryDto getCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id);
    }

}
