package pl.adrian.advertising_service.category;

import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.category.dto.CategoryDtoResponse;

import java.util.List;

@RestController
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDtoResponse> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/categories/{id}")
    public CategoryDtoResponse getCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id);
    }

    @PostMapping("/categories")
    public CategoryDtoResponse addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @PutMapping("/categories")
    public CategoryDtoResponse editCategory(@RequestBody Category category){
        return categoryService.editCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
    }

}
