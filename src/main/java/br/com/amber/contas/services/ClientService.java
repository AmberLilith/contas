package br.com.amber.contas.services;

import br.com.amber.contas.Status;
import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.dtos.ClientPatchDto;
import br.com.amber.contas.exceptions.ClientNotFoundException;
import br.com.amber.contas.exceptions.CpfAlreadyRegisteredException;
import br.com.amber.contas.exceptions.InvalidStatusException;
import br.com.amber.contas.exceptions.RegisterNotFoundByIdException;
import br.com.amber.contas.models.Client;
import br.com.amber.contas.repositories.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    public ClientDto findById(Long id) throws ClientNotFoundException{
        ClientDto clientDto = new ClientDto();
           Client client = repository.findById(id).orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado!"));
           BeanUtils.copyProperties(client, clientDto);
           return Optional.of(clientDto).get();
    }

    public ClientDto disableClient(Long id) throws ClientNotFoundException{
        Client client = repository.findById(id).orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado!"));
        client.setStatus(Status.INATIVO.name());
       return save(client);

    }

    public Page<ClientDto> findByStatus(Pageable pageable){
        Page<Client> pages = repository.findByStatus(Status.ATIVO.name(), pageable);
        return ClientDto.converter(pages);
    }

    public ClientDto update(ClientDto clientDto, Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado!"));
        BeanUtils.copyProperties(clientDto, client);
        Client returnedClient = repository.save(client);
        ClientDto returnedClientDto = new ClientDto();
        BeanUtils.copyProperties(returnedClient, returnedClientDto);
        return returnedClientDto;
    }
    }


