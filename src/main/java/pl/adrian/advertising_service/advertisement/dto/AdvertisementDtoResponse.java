package pl.adrian.advertising_service.advertisement.dto;


import lombok.Getter;
import lombok.Setter;
import pl.adrian.advertising_service.address.dto.AddressDtoResponse;
import pl.adrian.advertising_service.advertisement.Advertisement;

import java.time.LocalDateTime;


@Setter
@Getter
public class AdvertisementDtoResponse {
    private Long id;
    private String categoryName;
    private String username;
    private String name;
    private Float price;
    private String description;
    private AddressDtoResponse address;
    private LocalDateTime created;
    private LocalDateTime expired;
    private LocalDateTime modified;
    private Boolean active;
    private Integer duration;

    public AdvertisementDtoResponse(Advertisement advertisement){
        this.id = advertisement.getId();
        this.name = advertisement.getName();
        if (advertisement.getCategory() != null) {
            this.categoryName = advertisement.getCategory().getName();
        }
        this.username = advertisement.getUser().getUsername();
        this.price = advertisement.getPrice();
        this.description = advertisement.getDescription();
        this.created = advertisement.getCreated();
        this.expired = advertisement.getExpired();
        this.modified = advertisement.getModified();
        this.active = advertisement.getActive();
        this.duration = advertisement.getDuration();
    }

}
