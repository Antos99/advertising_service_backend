package pl.adrian.advertising_service.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.adrian.advertising_service.address.dto.AddressDtoRequest;
import pl.adrian.advertising_service.address.dto.AddressDtoResponse;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDtoRequest {
    @NotNull(message = "Category name cannot be null")
    private String categoryName;
    @NotNull(message = "Name cannot be null")
    private String name;
    @PositiveOrZero(message = "Price should not be negative")
    private Float price;
    @NotNull(message = "Description cannot be null")
    private String description;
    @NotNull(message = "Address cannot be null")
    @Valid
    private AddressDtoRequest address;
    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "Duration should be not less than 1")
    @Max(value = 365, message = "Duration should be not greater than 365")
    private Integer duration;
}
