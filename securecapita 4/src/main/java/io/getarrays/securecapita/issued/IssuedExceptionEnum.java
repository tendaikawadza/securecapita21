package io.getarrays.securecapita.issued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum IssuedExceptionEnum {


    ISSUED_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "ISSUED Not Found!");

 //   PURCHASE_REQUEST_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "Purchase Request Not Found!");

    private final HttpStatus status;
    private final String message;
    //private final HttpStatus status;
    //private final String message;
    //private final HttpStatus status;
    //private final String message;
}
