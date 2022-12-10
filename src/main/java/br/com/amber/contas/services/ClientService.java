package br.com.amber.contas.services;

import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.dtos.ClientPatchDto;
import br.com.amber.contas.exceptions.CpfAlreadyRegisteredException;
import br.com.amber.contas.exceptions.RegisterNotFoundByIdException;
import br.com.amber.contas.models.Client;
import br.com.amber.contas.repositories.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    public ClientDto save(Client client){
        try {
            repository.save(client);
            ClientDto clientDto = new ClientDto();
            BeanUtils.copyProperties(client, clientDto);
            return clientDto;
        }catch (DataIntegrityViolationException ex){
            throw new CpfAlreadyRegisteredException("CPF já cadastrado!");
        }
    }

    public Optional<ClientDto> findById(Long id){
        ClientDto clientDto = new ClientDto();
       try {
           Client client = repository.findById(id).get();
           BeanUtils.copyProperties(client, clientDto);
           return Optional.of(clientDto);
       }catch (NoSuchElementException ex){
           throw new RegisterNotFoundByIdException("Registro não encontrado com id " + id);
       }
    }

    public ClientDto updateName(Long id, ClientPatchDto clientPatchDto){
        Client client = repository.findById(id).get();
        client.setName(clientPatchDto.getName());
       return save(client);

    }
}
