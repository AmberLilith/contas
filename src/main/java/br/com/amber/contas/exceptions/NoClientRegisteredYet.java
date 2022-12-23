package br.com.amber.contas.exceptions;

public class NoClientRegisteredYet extends RuntimeException{

    public NoClientRegisteredYet(String message) {
        super(message);
    }
}
