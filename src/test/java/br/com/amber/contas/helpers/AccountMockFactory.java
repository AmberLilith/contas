package br.com.amber.contas.helpers;

import br.com.amber.contas.dtos.AccountDto;
import br.com.amber.contas.enums.AccountType;
import br.com.amber.contas.enums.Status;
import br.com.amber.contas.models.Account;
import br.com.amber.contas.services.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountMockFactory {

    public static Account createCurrentAccount(){
        return new Account(
                new UUID(2,10),
                Status.ATIVO.name(),
                0.0,
                1L,
                AccountType.CORRENTE.name()
        );
    }

    public static AccountDto createCurrentAccountDto(){
        return new AccountDto(
                Status.ATIVO.name(),
                0.0,
                1L,
                AccountType.CORRENTE.name()
        );
    }

    public static List<AccountDto> createAListOfThreeAccountDtos(){
        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(new AccountDto(Status.ATIVO.name(),0.0, 1L, AccountType.CORRENTE.name()));
        accountDtoList.add(new AccountDto(Status.ATIVO.name(),0.0, 1L, AccountType.POUPANCA.name()));
        accountDtoList.add(new AccountDto(Status.ATIVO.name(),0.0, 1L, AccountType.SALARIO.name()));
        return accountDtoList;

    }

}
