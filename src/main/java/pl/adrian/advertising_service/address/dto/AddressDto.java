package pl.adrian.advertising_service.address.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.adrian.advertising_service.address.Address;

@Setter
@Getter
@NoArgsConstructor
public class AddressDto {
    private Long advertisementId;
    private String voivodeship;
    private String city;
    private String postCode;
    private String street;

    public AddressDto(Address address) {
        this.advertisementId = address.getId();
        this.voivodeship = address.getVoivodeship().getDisplayValue();
        this.city = address.getCity();
        this.postCode = address.getPostCode();
        this.street = address.getStreet();
    }
}
