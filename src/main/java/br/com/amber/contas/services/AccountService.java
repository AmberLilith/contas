package br.com.amber.contas.services;

import br.com.amber.contas.dtos.AccountDto;
import br.com.amber.contas.exceptions.ClientNotFoundException;
import br.com.amber.contas.models.Account;
import br.com.amber.contas.models.Client;
import br.com.amber.contas.repositories.AccountRepository;
import br.com.amber.contas.repositories.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    public AccountDto save(Account account){
        Client clientExistent = clientRepository.findById(account.getClientId()).orElseThrow(() -> new ClientNotFoundException(String.format("Cliente com %d não encontrado!", account.getClientId())));
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(accountRepository.save(account), accountDto);
        return accountDto;
    }
}
