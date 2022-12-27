package pl.adrian.advertising_service.exceptions;

import java.time.ZonedDateTime;

public record ApiException(Object message, int status, String error, String path, ZonedDateTime timestamp) {
}
