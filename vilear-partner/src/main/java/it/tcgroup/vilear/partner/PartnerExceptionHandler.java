package it.tcgroup.vilear.partner;

import it.tcgroup.vilear.base.Payload.Exception.AlreadyExistsException;
import it.tcgroup.vilear.base.Payload.Exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PartnerExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String>errorHandler(Throwable exception){
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        if(exception instanceof NotFoundException){
            code = HttpStatus.NOT_FOUND;
        }else if(exception instanceof AlreadyExistsException){
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(exception.getMessage(),code);
    }

}
