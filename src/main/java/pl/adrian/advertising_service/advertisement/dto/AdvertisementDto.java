package pl.adrian.advertising_service.advertisement.dto;


import lombok.Getter;
import lombok.Setter;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.address.dto.AddressDto;
import pl.adrian.advertising_service.advertisement.Advertisement;

import java.time.LocalDateTime;


@Setter
@Getter
public class AdvertisementDto {
    private Long id;
    private Long categoryId;
    private AddressDto advertisementAddress;
    private String name;
    private Float price;
    private String description;
    private Address address;
    private LocalDateTime created;
    private LocalDateTime expired;
    private LocalDateTime modified;
    private Boolean active;
    private Integer duration;

    public AdvertisementDto(Advertisement advertisement){
        this.id = advertisement.getId();
        this.name = advertisement.getName();
        this.categoryId = advertisement.getCategory().getId();
        this.price = advertisement.getPrice();
        this.description = advertisement.getDescription();
        this.created = advertisement.getCreated();
        this.expired = advertisement.getExpired();
        this.modified = advertisement.getModified();
        this.active = advertisement.getActive();
        this.duration = advertisement.getDuration();
    }

}
