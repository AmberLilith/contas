package br.com.amber.contas.repositories;

import br.com.amber.contas.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {



    @Query("select c from Client c where c.status = ?1")
    Page<Client> findByStatus(String status, Pageable pageable);

}
