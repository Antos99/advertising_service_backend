package pl.adrian.advertising_service.advertisement;

import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoRequest;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoResponse;

import java.util.List;

@RestController
public class AdvertisementController {
    AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/advertisements")
    public List<AdvertisementDtoResponse> getAdvertisements(){
        return advertisementService.getAdvertisements();
    }

    @GetMapping("/advertisements/{id}")
    public AdvertisementDtoResponse getAdvertisement(@PathVariable("id") Long id){
        return advertisementService.getAdvertisement(id);
    }

    @GetMapping("/advertisements/filter")
    public List<AdvertisementDtoResponse> getAdvertisementsByFilter(
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
    public AdvertisementDtoResponse addAdvertisement(@RequestBody AdvertisementDtoRequest advertisementDtoRequest){
        return advertisementService.addAdvertisement(advertisementDtoRequest);
    }

    @PutMapping("/advertisements")
    public AdvertisementDtoResponse editAdvertisement(@RequestBody AdvertisementDtoRequest advertisementDtoRequest){
        return advertisementService.editAdvertisement(advertisementDtoRequest);
    }

    @DeleteMapping("/advertisements/{id}")
    public void deleteAdvertisement(@PathVariable("id") Long id){
        advertisementService.deleteAdvertisement(id);
    }
}
