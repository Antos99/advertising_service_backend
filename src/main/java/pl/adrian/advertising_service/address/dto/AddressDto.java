package pl.adrian.advertising_service.address.dto;

import lombok.Getter;
import lombok.Setter;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.advertisement.Advertisement;
import pl.adrian.advertising_service.advertisement.AdvertisementVoivodeship;

import javax.persistence.*;
@Setter
@Getter
public class AddressDto {
    private Long id;
    private AdvertisementVoivodeship voivodeship;
    private String city;
    private String postCode;
    private String street;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.voivodeship = address.getVoivodeship();
        this.city = address.getCity();
        this.postCode = address.getPostCode();
        this.street = address.getStreet();
    }
}
