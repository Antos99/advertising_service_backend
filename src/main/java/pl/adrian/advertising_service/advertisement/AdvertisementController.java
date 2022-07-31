package pl.adrian.advertising_service.advertisement;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDto;

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
    public List<AdvertisementDto> getAdvertisementsByFilter(
            @RequestParam(name="categoryId") Long categoryId,
            @RequestParam(name="pageNumber", required=false, defaultValue="0") Integer pageNumber,
            @RequestParam(name="pageSize", required=false, defaultValue="1") Integer pageSize,
            @RequestParam(name="sortBy", required = false, defaultValue="id") String property,
            @RequestParam(name="orderBy", required = false, defaultValue="DESC") Sort.Direction order
            ){
        Integer pageNumberResult = pageNumber > 0 ? pageNumber : 0;

        Integer pageSizeResult;
        if(pageSize < 1){
            pageSizeResult = 1;
        } else if(pageSize > 500){
            pageSizeResult = 500;
        } else{
            pageSizeResult = pageSize;
        }

        return advertisementService.getAdvertisementsByFilter(categoryId, pageNumberResult, pageSizeResult, order,
                property);
    }

    @PostMapping("/advertisements")
    public AdvertisementDto addAdvertisement(@RequestBody AdvertisementDto advertisementDto){
        return advertisementService.addAdvertisement(advertisementDto);
    }

    @PutMapping("/advertisements")
    public AdvertisementDto editAdvertisement(@RequestBody AdvertisementDto advertisementDto){
        return advertisementService.editAdvertisement(advertisementDto);
    }

    @DeleteMapping("/advertisements/{id}")
    public void deleteAdvertisement(@PathVariable("id") Long id){
        advertisementService.deleteAdvertisement(id);
    }


}
