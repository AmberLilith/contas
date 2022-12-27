package br.com.amber.contas.services;

import br.com.amber.contas.dtos.AccountDto;
import br.com.amber.contas.exceptions.ClientNotFoundException;
import br.com.amber.contas.models.Account;
import br.com.amber.contas.models.Client;
import br.com.amber.contas.repositories.AccountRepository;
import br.com.amber.contas.repositories.ClientRepository;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    public AccountDto save(Account account){
        //TODO chamar direto o findById do repository é melhor que chamar o da própria service como abaixo?
        Client clientExistent = clientRepository.findById(account.getClientId()).orElseThrow(() -> new ClientNotFoundException(String.format("Cliente com %d não encontrado!", account.getClientId())));
        AccountDto accountDto = new AccountDto();
        //TODO eu estava recebendo o AccountDto nos parametros e usando ele para retornar no final. Dai a dúvida: o melhor e fazer isso ou retornar o que o repository.save retorna?
        BeanUtils.copyProperties(accountRepository.save(account), accountDto);
        return accountDto;
    }

    public List<AccountDto> findByClientId(Long id) {
        return accountRepository.findByClientId(id);
    }
}
