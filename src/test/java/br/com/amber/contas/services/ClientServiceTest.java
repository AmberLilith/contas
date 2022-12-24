package br.com.amber.contas.services;

import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.dtos.StatusDto;
import br.com.amber.contas.exceptions.ClientNotFoundException;
import br.com.amber.contas.exceptions.CpfAlreadyRegisteredException;
import br.com.amber.contas.helpers.ClientMockFactory;
import br.com.amber.contas.models.Client;
import br.com.amber.contas.repositories.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@SpringBootTest
public class ClientServiceTest {

    @MockBean
    ClientRepository repository;

    @Autowired
    ClientService service;

    @Test
    @DisplayName("Ao salvar cliente com sucesso, returnar dados cadastrados!")
    public void when_successfullySavingClient_then_returnRegisteredData(){
        //given
        Client client= ClientMockFactory.createClientJoaoDaSilva();
        Mockito.when(repository.save(client)).thenReturn(client);

        //when
        ClientDto response = service.save(client);

        //then
        Assertions.assertEquals("78945612301", response.getCpf());
    }

    @Test
    @DisplayName("Ao salvar cliente, se cpf já existir, lançar CpfAlreadyRegisteredException!")
    public void when_savingClient_and_cpfAlreadyExists_then_trhowCpfAlreadyRegisteredException(){
        //given
        Client client= ClientMockFactory.createClientJoaoDaSilva();
        Mockito.when(repository.save(client)).thenThrow(new DataIntegrityViolationException("CPF já cadastrado!"));

        //when //then
        Assertions.assertThrows(CpfAlreadyRegisteredException.class, () -> service.save(client));
    }

    @Test
    @DisplayName("Ao buscar cliente pelo id, retornar uma Optional de ClientDto")
    public void when_findClientById_then_returnAnOptionalOfClientDto(){
        //given
        Optional<Client> optionalClient = ClientMockFactory.createOptionalOfClient();
        Client client = ClientMockFactory.createClientJoaoDaSilva();
        Mockito.when(repository.findById(1L)).thenReturn(optionalClient);

        //when
        ClientDto clientDtoReturned = service.findById(1L);

        //then
        Assertions.assertEquals("78945612301", clientDtoReturned.getCpf());
    }


    @Test
    @DisplayName("Ao buscar cliente pelo id e cliente não existir,lançar ClientNotFoundException")
    public void when_findClientById_and_clientDoesNotExist_then_throwClientNotFoundException(){
        //given
        Optional<Client> optionalClient = ClientMockFactory.createOptionalOfClient();
        Client client = ClientMockFactory.createClientJoaoDaSilva();
        Mockito.when(repository.findById(1546465L)).thenThrow(new ClientNotFoundException("Cliente não encontrado!"));

        //when //then
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.findById(1546465L));
    }

    @Test
    @DisplayName("Ao desativar cliente, retornar um ClientDto com status = 'INATIVO'")
    public void when_successfullyDisablingClient_then_returnClientDtoWithStatusEqualsInativo(){
        //given
        Client client = ClientMockFactory.createClientJoaoDaSilva();
        Optional<Client> optionalClient = ClientMockFactory.createOptionalOfClient();
        Mockito.when(repository.findById(1L)).thenReturn(optionalClient);
        Mockito.when(repository.save(client)).thenReturn(client);

        //when
        ClientDto clientDtoResponse = service.disableClient(1L);

        //then
        Assertions.assertEquals("INATIVO", clientDtoResponse.getStatus());
    }

    @Test
    @DisplayName("Ao desativar cliente e cliente não existir, lançar ClientNotFoundException'")
    public void when_disablingClient_and_clientDoesNotExist_then_trhowClientNotFoundException(){
        //given
        Client client = ClientMockFactory.createClientJoaoDaSilva();
        Optional<Client> optionalClient = ClientMockFactory.createOptionalOfClient();
        Mockito.when(repository.findById(1556464L)).thenThrow(new ClientNotFoundException("Cliente não encontrado!"));

        //when //then
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.disableClient(1556464L));
    }


    @Test
    @DisplayName("Ao buscar clientes por status, retornar um Page de ClientDto")
    public void when_findClientByStatus_thenReturnAPageOfClientDto(){
        //given
        Page<Client> page = ClientMockFactory.createPageOfThreeClients();
        StatusDto statusDto = new StatusDto("ATIVO");
        Pageable pageable = PageRequest.of(0,1);
        Mockito.when(repository.findByStatus(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(page);

        //when
        Page<ClientDto> pageResponse = service.findByStatus(pageable, statusDto.getStatus());

        //then
        Assertions.assertEquals("12345678941", pageResponse.getContent().get(0).getCpf());
    }

    @Test
    @DisplayName("Ao alterar dados do cliente com sucesso, retornar retornar status code 200 e ClientDto com dados alterados")
    public void when_successfullyUpdatingClient_then_returnStatusCode200AndClientDtoWithUpdatedData(){

        //given
        ClientDto clientDtoUpdated = ClientMockFactory.createClientDtoJoaoDaSilva();
        clientDtoUpdated.setCpf("12345678996");
        clientDtoUpdated.setName("João da Silva Souza");
        Client clientUpdated = new Client();
        BeanUtils.copyProperties(clientDtoUpdated, clientUpdated);
        Optional<Client> optionalClient = ClientMockFactory.createOptionalOfClient();
        Mockito.when(repository.findById(1L)).thenReturn(optionalClient);
        Mockito.when(repository.save(Mockito.any(Client.class))).thenReturn(clientUpdated);

        //when
        ClientDto clientResponse = service.update(clientDtoUpdated, 1L);

        //then
        Assertions.assertEquals("12345678996", clientResponse.getCpf());
    }

    @Test
    @DisplayName("Ao alterar dados do cliente e cliente não existir, lançar ClientNotFoundException")
    public void when_updatingClient_and_clientDoesNotExist_then_throwClientNotFoundException(){

        //given
        Mockito.when(repository.findById(1L)).thenThrow(new ClientNotFoundException("Cliente não encontrado!"));

        //when //then
        Assertions.assertThrows(ClientNotFoundException.class, () -> service.update(Mockito.any(ClientDto.class), 1L));
    }

}
