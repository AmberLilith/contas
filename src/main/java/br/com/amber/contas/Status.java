package br.com.amber.contas;

public enum Status {

    ATIVO,
    INATIVO;

    public static String to_String() {
        String toString = "";
        for(Status status : Status.values()){
            toString.concat(status.name()).concat(",");
        }
        return toString;
    }
}



