package pl.adrian.advertising_service.exceptions;

public class BadRequestException extends Exception{

    public BadRequestException(String message) {
        super(message);
    }
}