package br.com.amber.contas.exceptions;

public class CpfAlreadyRegisteredException extends RuntimeException{

    public CpfAlreadyRegisteredException(String message) {
        super(message);
    }
}
