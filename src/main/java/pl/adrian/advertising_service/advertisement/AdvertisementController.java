package pl.adrian.advertising_service.advertisement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDto;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoMapper;

import java.util.List;

@RestController
public class AdvertisementController {
    AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/advertisements")
    public List<AdvertisementDto> getAdvertisements(){
        return advertisementService.getAdvertisements();
    }

    @GetMapping("/advertisements/{id}")
    public AdvertisementDto getAdvertisement(@PathVariable("id") Long id){
        return advertisementService.getAdvertisement(id);
    }

    @GetMapping("/advertisements/filter")
    public List<AdvertisementDto> getAdvertisementsByFilter(@RequestParam(name="categoryId") Long id){
        return advertisementService.getAdvertisementsByCategoryId(id);
    }



}
