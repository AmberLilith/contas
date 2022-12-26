package br.com.amber.contas.helpers;

import br.com.amber.contas.enums.Status;
import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientMockFactory {

    public static Client createClientJoaoDaSilva(){
        Client client = new Client();
        client.setName("João da Silva");
        client.setCpf("78945612301");
        client.setStatus(Status.ATIVO.name());
        return client;
    }

    public static ClientDto createClientDtoJoaoDaSilva(){
        ClientDto clientDto = new ClientDto();
        clientDto.setName("João da Silva");
        clientDto.setCpf("78945612301");
        clientDto.setStatus(Status.ATIVO.name());
        return clientDto;
    }

    public static List<Client> createListOfThreeClients(){
        Client client1 = new Client(1L, "Maria da silva", "12345678941", "ATIVO");
        Client client2 = new Client(2L, "José Tadeu", "12378945674", "ATIVO");
        Client client3 = new Client(3L, "João Pereira", "78912345696", "ATIVO");
        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        return clients;
    }

    public static List<ClientDto> createListOfThreeClientDtos(){
        ClientDto client1 = new ClientDto("Maria da Silva", "12345678941", "ATIVO");
        ClientDto client2 = new ClientDto("José Tadeu", "12378945674", "ATIVO");
        ClientDto client3 = new ClientDto("João Pereira", "78912345696", "ATIVO");
        List<ClientDto> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        return clients;
    }

    public static Page<ClientDto> createPageOfThreeClientDtos(){
        PageRequest pagination = PageRequest.of(0, 3);
        List<ClientDto> clientDtos = createListOfThreeClientDtos();
        return  new PageImpl<>(clientDtos, pagination, clientDtos.size());
    }

    public static Page<Client> createPageOfThreeClients(){
        PageRequest pagination = PageRequest.of(0, 3);
        List<Client> clients = createListOfThreeClients();
        return  new PageImpl<>(clients, pagination, clients.size());
    }

    public static Optional<ClientDto> createOptionalOfClientDto(){
        return Optional.of(createClientDtoJoaoDaSilva());
    }

    public static Optional<Client> createOptionalOfClient(){
        return Optional.of(createClientJoaoDaSilva());
    }

}
