package br.com.amber.contas.repositories;

import br.com.amber.contas.dtos.AccountDto;
import br.com.amber.contas.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<AccountDto> findByClientId(Long id);
}
