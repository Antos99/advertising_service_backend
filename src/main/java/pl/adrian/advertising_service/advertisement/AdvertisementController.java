package pl.adrian.advertising_service.advertisement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoPostRequest;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoPutRequest;
import pl.adrian.advertising_service.advertisement.dto.AdvertisementDtoResponse;
import pl.adrian.advertising_service.exceptions.BadRequestException;
import pl.adrian.advertising_service.exceptions.ForbiddenException;
import pl.adrian.advertising_service.exceptions.NotFoundException;
import pl.adrian.advertising_service.security.CustomUserDetails;


import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
@Validated
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @GetMapping("/advertisements")
    @Operation(summary = "Get all advertisements")
    public ResponseEntity<List<AdvertisementDtoResponse>> getAdvertisements(
            @RequestParam(name="pageNumber", required=false, defaultValue="0")
            @Min(value = 0, message = "Page number should be not less than 0")
            Integer pageNumber,
            @RequestParam(name="pageSize", required=false, defaultValue="1")
            @Min(value = 1, message = "Page size should be not less than 1")
            @Max(value = 500, message = "Page size should be not less than 1")
            Integer pageSize,
            @RequestParam(name="sortBy", required = false, defaultValue="id")
            @Pattern(regexp = "id|price|created", message = "Wrong sortBy parameter! Possible options: id, price, created")
            String property,
            @RequestParam(name="orderBy", required = false, defaultValue="DESC")
            @Pattern(regexp = "ASC|asc|DESC|desc",
                    message = "Wrong orderBy parameter! Possible options: ASC (ascending), DESC (descending)")
            String order
    ){
        Direction direction = Direction.fromString(order);
        return new ResponseEntity<>(
                advertisementService.getAdvertisements(pageNumber, pageSize, direction, property), HttpStatus.OK);
    }

    @GetMapping("/advertisements/{id}")
    @Operation(summary = "Get advertisement by id")
    public ResponseEntity<AdvertisementDtoResponse> getAdvertisement(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(advertisementService.getAdvertisement(id), HttpStatus.OK);
    }

    @GetMapping("/advertisements/category/{categoryName}")
    @Operation(summary = "Get advertisements by category name")
    public ResponseEntity<List<AdvertisementDtoResponse>> getAdvertisementsByCategory(
            @PathVariable(name="categoryName")
            String categoryName,
            @RequestParam(name="pageNumber", required=false, defaultValue="0")
            @Min(value = 0, message = "Page number should be not less than 0")
            Integer pageNumber,
            @RequestParam(name="pageSize", required=false, defaultValue="1")
            @Min(value = 1, message = "Page size should be not less than 1")
            @Max(value = 500, message = "Page size should be not less than 1")
            Integer pageSize,
            @RequestParam(name="sortBy", required = false, defaultValue="id")
            @Pattern(regexp = "id|price|created", message = "Wrong sortBy parameter! Possible options: id, price, created")
            String property,
            @RequestParam(name="orderBy", required = false, defaultValue="DESC")
            @Pattern(regexp = "ASC|asc|DESC|desc",
                    message = "Wrong orderBy parameter! Possible options: ASC (ascending), DESC (descending)")
            String order
            ) throws BadRequestException {
        Direction direction = Direction.fromString(order);
        return new ResponseEntity<>(advertisementService.getAdvertisementsByCategoryName(
                categoryName, pageNumber, pageSize, direction, property), HttpStatus.OK);
    }

    @GetMapping("/advertisements/user/{username}")
    @Operation(summary = "Get advertisements by username")
    public ResponseEntity<List<AdvertisementDtoResponse>> getAdvertisementsByUsername(
            @PathVariable(name="username")
            String username,
            @RequestParam(name="pageNumber", required=false, defaultValue="0")
            @Min(value = 0, message = "Page number should be not less than 0")
            Integer pageNumber,
            @RequestParam(name="pageSize", required=false, defaultValue="1")
            @Min(value = 1, message = "Page size should be not less than 1")
            @Max(value = 500, message = "Page size should be not less than 1")
            Integer pageSize,
            @RequestParam(name="sortBy", required = false, defaultValue="id")
            @Pattern(regexp = "id|price|created", message = "Wrong sortBy parameter! Possible options: id, price, created")
            String property,
            @RequestParam(name="orderBy", required = false, defaultValue="DESC")
            @Pattern(regexp = "ASC|asc|DESC|desc",
                    message = "Wrong orderBy parameter! Possible options: ASC (ascending), DESC (descending)")
            String order
    ) throws BadRequestException {
        Direction direction = Direction.fromString(order);
        return new ResponseEntity<>(advertisementService
                .getAdvertisementsByUsername(username, pageNumber, pageSize, direction, property), HttpStatus.OK);
    }

    @PostMapping("/advertisements")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Add new advertisement. Only authenticated users can add advertisement"
    )
    public ResponseEntity<AdvertisementDtoResponse> addAdvertisement(
            @Valid @RequestBody AdvertisementDtoPostRequest advertisementDtoPostRequest,
            Authentication authentication)
            throws BadRequestException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(advertisementService
                .addAdvertisement(advertisementDtoPostRequest, userDetails), HttpStatus.CREATED);
    }

    @PutMapping("/advertisements/{id}")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Edit advertisement. Only USER who created the advertisement, MODERATOR and ADMIN can edit advertisement"
    )
    public ResponseEntity<AdvertisementDtoResponse> editAdvertisement(
            @RequestBody AdvertisementDtoPutRequest advertisementDto,
            @PathVariable("id") Long id,
            Authentication authentication)
            throws NotFoundException, ForbiddenException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(advertisementService
                .editAdvertisement(advertisementDto, id, userDetails), HttpStatus.OK);
    }

    @DeleteMapping("/advertisements/{id}")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Delete advertisement. Only USER who created the advertisement, MODERATOR and ADMIN can delete advertisement"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdvertisement(@PathVariable("id") Long id, Authentication authentication) throws NotFoundException, ForbiddenException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        advertisementService.deleteAdvertisement(id, userDetails);
    }

}
