package br.com.amber.contas.enums;

public enum AccountType {
    CORRENTE,
    POUPANCA,
    SALARIO;

    public static String to_string(){
        String to_string = "{";
        for(AccountType type : AccountType.values()){
            to_string.concat(type.name()).concat(",");
            }
        to_string.concat("}");
        return to_string;
    }
}
