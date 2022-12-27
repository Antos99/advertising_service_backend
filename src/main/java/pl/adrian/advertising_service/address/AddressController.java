package pl.adrian.advertising_service.address;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.address.dto.AddressDtoResponse;
import pl.adrian.advertising_service.exceptions.NotFoundException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/addresses")
    @Operation(summary = "Get all addresses of advertisements")
    public ResponseEntity<List<AddressDtoResponse>> getAddresses(
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
        Sort.Direction direction = Sort.Direction.fromString(order);
        return new ResponseEntity<>(
                addressService.getAddresses(pageNumber, pageSize, direction, property), HttpStatus.OK);
    }

    @GetMapping("/addresses/{id}")
    @Operation(summary = "Get address of advertisement by id")
    public ResponseEntity<AddressDtoResponse> getAddress(@PathVariable(name="id") Long id) throws NotFoundException {
        AddressDtoResponse addressDtoResponse = addressService.getAddress(id);
        return new ResponseEntity<>(addressDtoResponse, HttpStatus.OK);
    }
}
