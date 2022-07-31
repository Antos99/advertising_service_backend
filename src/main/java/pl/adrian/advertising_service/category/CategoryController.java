package pl.adrian.advertising_service.category;

import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.advertisement.Advertisement;
import pl.adrian.advertising_service.category.dto.CategoryDto;
import pl.adrian.advertising_service.category.dto.CategoryDtoMapper;

import javax.websocket.server.PathParam;
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

    @PostMapping("/categories")
    public CategoryDto addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @PutMapping("/categories")
    public CategoryDto editCategory(@RequestBody Category category){
        return categoryService.editCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
    }

}
