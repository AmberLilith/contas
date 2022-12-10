package br.com.amber.contas.exceptions;

public class RegisterNotFoundByIdException extends RuntimeException{

    public RegisterNotFoundByIdException(String message){
        super(message);
    }
}
