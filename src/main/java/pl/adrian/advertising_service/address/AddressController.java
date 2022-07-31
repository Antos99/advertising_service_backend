package pl.adrian.advertising_service.address;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.advertising_service.address.dto.AddressDto;

import java.util.List;

@RestController
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/addresses")
    public List<AddressDto> getAddresses(){
        return addressService.getAddresses();
    }

    @GetMapping("/addresses/{id}")
    public AddressDto getAddress(@PathVariable(name="id") Long id){
        return addressService.getAddress(id);
    }
}
