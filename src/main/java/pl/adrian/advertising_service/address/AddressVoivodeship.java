package pl.adrian.advertising_service.address;

import java.util.Arrays;

public enum AddressVoivodeship {
    DOLNOSLASKIE("Dolnośląskie"),
    KUJAWSKO_POMORSKIE("Kujawsko-pomorskie"),
    LUBELSKIE("Lubelskie"),
    LUBUSKIE("Lubuskie"),
    LODZKIE("Łódzkie"),
    MALOPOLSKIE("Małopolskie"),
    MAZOWIECKIE("Mazowieckie"),
    OPOLSKIE("Opolskie"),
    PODKARPACKIE("Podkarpackie"),
    PODLASKIE("Podlaskie"),
    POMORSKIE("Pomorskie"),
    SLASKIE("Śląskie"),
    SWIETOKRZYSKIE("Świętokrzyskie"),
    WARMINSKO_MAZURSKIE("Warmińsko-mazurskie"),
    WIELKOPOLSKIE("Wielkopolskie"),
    ZACHODNIOPOMORSKIE("Zachodniopomorskie");

    private final String displayValue;

    AddressVoivodeship(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }

    public static AddressVoivodeship getByValue(String value) {
        return Arrays.stream(AddressVoivodeship.values())
                .filter(voivodeship -> voivodeship.getDisplayValue().equalsIgnoreCase(value))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Voivodeship "+ value + " does not exist"));
    }
}
