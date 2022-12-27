package pl.adrian.advertising_service.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDtoRequest {
    @NotNull(message = "Category name cannot be null")
    private String name;
}
