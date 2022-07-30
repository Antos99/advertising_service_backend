package pl.adrian.advertising_service.advertisement;

public enum AdvertisementVoivodeship {
    DOLNOŚLĄSKIE("Dolnośląskie"),
    KUJAWSKO_POMORSKIE("Kujawsko-pomorskie"),
    LUBELSKIE("Lubelskie"),
    LUBUSKIE("Lubuskie"),
    ŁÓDZKIE("Łódzkie"),
    MAŁOPOLSKIE("Małopolskie"),
    MAZOWIECKIE("Mazowieckie"),
    OPOLSKIE("Opolskie"),
    PODKARPACKIE("Podkarpackie"),
    PODLASKIE("Podlaskie"),
    POMORSKIE("Pomorskie"),
    ŚLĄSKIE("Śląskie"),
    ŚWIĘTOKRZYSKIE("Świętokrzyskie"),
    WARMIŃSKO_MAZURSKIE("Warmińsko-mazurskie"),
    WIELKOPOLSKIE("Wielkopolskie"),
    ZACHODNIOPOMORSKIE("Zachodniopomorskie");

    private final String displayValue;

    AdvertisementVoivodeship(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
}
