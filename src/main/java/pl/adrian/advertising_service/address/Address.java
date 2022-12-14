package pl.adrian.advertising_service.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.adrian.advertising_service.advertisement.Advertisement;

import javax.persistence.*;

@Entity
@ToString(exclude = "advertisement")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="addresses")
public class Address {
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    @MapsId
    @JoinColumn(name="advertisement_id")
    private Advertisement advertisement;
    @Column(name="voivodeship")
    @Enumerated(EnumType.STRING)
    private AddressVoivodeship voivodeship;
    @Column(name="city")
    private String city;
    @Column(name="post_code")
    private String postCode;
    @Column(name="street")
    private String street;

    public Address(
            Advertisement advertisement, AddressVoivodeship voivodeship, String city, String postCode, String street){
        this.advertisement = advertisement;
        this.voivodeship = voivodeship;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
    }
}
