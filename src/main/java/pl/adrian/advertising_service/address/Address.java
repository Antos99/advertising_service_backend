package pl.adrian.advertising_service.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.adrian.advertising_service.advertisement.Advertisement;
import pl.adrian.advertising_service.advertisement.AdvertisementVoivodeship;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoMapper;

import javax.persistence.*;

@Entity
@ToString
@Setter
@Getter
@Table(name="addresses")
public class Address {
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="advertisement_id")
    private Advertisement advertisement;
    @Column(name="voivodeship")
    @Enumerated(EnumType.STRING)
    private AdvertisementVoivodeship voivodeship;
    @Column(name="city")
    private String city;
    @Column(name="post_code")
    private String postCode;
    @Column(name="street")
    private String street;


}
