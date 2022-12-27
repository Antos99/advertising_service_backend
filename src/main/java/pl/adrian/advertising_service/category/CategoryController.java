package pl.adrian.advertising_service.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.category.dto.CategoryDtoRequest;
import pl.adrian.advertising_service.category.dto.CategoryDtoResponse;
import pl.adrian.advertising_service.exceptions.ConflictException;
import pl.adrian.advertising_service.exceptions.NotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/categories")
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<CategoryDtoResponse>> getCategories(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/categories/{name}")
    @Operation(summary = "Get category by its name")
    public ResponseEntity<CategoryDtoResponse> getCategory(@PathVariable("name") String name) throws NotFoundException {
        return new ResponseEntity<>(categoryService.getCategory(name), HttpStatus.OK);
    }

    @PostMapping("/categories")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Add new category. Only ADMIN can add new category"
    )
    public ResponseEntity<CategoryDtoResponse> addCategory(@RequestBody @Valid CategoryDtoRequest category) throws ConflictException {
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/categories/{name}")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Edit categories name. Only ADMIN can edit categories name"
    )
    public ResponseEntity<CategoryDtoResponse> editCategory(
            @PathVariable String name,
            @RequestBody @Valid CategoryDtoRequest category
    )
            throws NotFoundException, ConflictException {
        return new ResponseEntity<>(categoryService.editCategory(name, category), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{name}")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Delete category. Only ADMIN can delete category"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("name") String name) throws NotFoundException {
        categoryService.deleteCategory(name);
    }

}
