package pl.adrian.advertising_service.advertisement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.adrian.advertising_service.address.dto.AddressDto;

@Setter
@Getter
@NoArgsConstructor
public class AdvertisementDtoRequest {
    private Long id;
    private Long categoryId;
    private String name;
    private Float price;
    private String description;
    private AddressDto address;
    private Integer duration;
}
