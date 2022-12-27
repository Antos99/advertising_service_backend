package pl.adrian.advertising_service.address.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDtoRequest {
    @Pattern(regexp =
            "[Dd]olnośląskie|" +
            "[Kk]ujawsko-pomorskie|" +
            "[Ll]ubelskie|" +
            "[Ll]ubuskie|" +
            "[Łł]ódzkie|" +
            "[Mm]ałopolskie|" +
            "[Mm]azowieckie|" +
            "[Oo]polskie|" +
            "[Pp]odkarpackie|" +
            "[Pp]odlaskie|" +
            "[Pp]omorskie|" +
            "[Śś]ląskie|" +
            "[Śś]więtokrzyskie|" +
            "[Ww]armińsko-mazurskie|" +
            "[Ww]ielkopolskie|" +
            "[Zz]achodniopomorskie", message = "Wrong voivodeship!")
    @NotNull(message = "Voivodeship cannot be null")
    private String voivodeship;
    @NotNull(message = "City cannot be null")
    private String city;
    @NotNull(message = "Post code cannot be null")
    @Pattern(regexp = "\\d\\d-\\d\\d\\d", message = "Post code should have valid syntax")
    private String postCode;
    @NotNull(message = "Street cannot be null")
    private String street;
}
