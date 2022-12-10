package br.com.amber.contas.validations;



import br.com.amber.contas.exceptions.CpfAlreadyRegisteredException;
import br.com.amber.contas.exceptions.RegisterNotFoundByIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CpfAlreadyRegisteredException.class)
    public String handleCpfAlreadyRegisteredException(CpfAlreadyRegisteredException ex) {
        return "{\"cpf\":\"" + ex.getMessage() + "\"}";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RegisterNotFoundByIdException.class)
    public String handleRegisterNotFoundByIdException(RegisterNotFoundByIdException ex) {
        return "{\"id\":\"" + ex.getMessage() + "\"}";
    }

   /* @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleBusinessException(UserNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }*/

}