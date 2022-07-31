package pl.adrian.advertising_service.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.adrian.advertising_service.category.Category;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private Long numberOfAdvertisements;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.numberOfAdvertisements = 0L;
    }
}
