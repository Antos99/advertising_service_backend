package pl.adrian.advertising_service.category.dto;

import lombok.Getter;
import lombok.Setter;
import pl.adrian.advertising_service.category.Category;

@Getter
@Setter
public class CategoryDtoResponse {
    private Long id;
    private String name;
    private Long numberOfAdvertisements;

    public CategoryDtoResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.numberOfAdvertisements = 0L;
    }
}
