package br.com.amber.contas.controllers;

import br.com.amber.contas.Status;
import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.exceptions.ClientNotFoundException;
import br.com.amber.contas.exceptions.CpfAlreadyRegisteredException;
import br.com.amber.contas.helpers.ClientMockFactory;
import br.com.amber.contas.models.Client;
import br.com.amber.contas.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public  class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    ClientDto CLIENT_DTO = ClientMockFactory.createClientDtoJoaoDaSilva();

    @Test
    @DisplayName("Ao cadastrar um novo cliente, retornar status code 201 e dados do cliente cadastrado!")
    public void when_registerNewClient_then_returnStatusCodeCreated() throws Exception {
        this.mockMvc
                .perform(
                        post("/v1/clientes/cadastrar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(CLIENT_DTO))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\":\"João da Silva\",\"cpf\":\"78945612301\",\"status\":\"ATIVO\"}"));
    }

    @Test
    @DisplayName("Ao cadastrar um novo cliente, se cliente já existir, retornar status code 400!")
    public void when_registerNewClient_and_clientIsAlreadyRegistered_then_returnStatusCodeBadRequest() throws Exception {
        Mockito.when(service.save(Mockito.any(Client.class))).thenThrow(new CpfAlreadyRegisteredException("CPF já cadastrado!"));
        this.mockMvc
                .perform(
                        post("/v1/clientes/cadastrar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(CLIENT_DTO))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"cpf\":\"CPF já cadastrado!\"}"));
    }

    @Test
    @DisplayName("Ao cadastrar um novo cliente, se nome do cliente estiver vazio, retornar status code 400!")
    public void when_registerNewClient_and_clientNameIsEmpty_then_returnStatusCodeBadRequest() throws Exception {
        ClientDto clientDtoWithEmptyName = CLIENT_DTO;
        clientDtoWithEmptyName.setName("");
        this.mockMvc
                .perform(
                        post("/v1/clientes/cadastrar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(clientDtoWithEmptyName))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"name\":\"não pode ficar em branco!\"}"));
    }

    @Test
    @DisplayName("Ao cadastrar um novo cliente, se cpf do cliente estiver vazio, retornar status code 400!")
    public void when_registerNewClient_and_clientCpfIsEmpty_then_returnStatusCodeBadRequest() throws Exception {
        ClientDto clientDtoWithEmptyName = CLIENT_DTO;
        clientDtoWithEmptyName.setCpf("");
        this.mockMvc
                .perform(
                        post("/v1/clientes/cadastrar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(clientDtoWithEmptyName))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"cpf\":\"não pode ficar em branco e deve conter exatamente 11 dígitos inteiros não negativos!\"}"));
    }

    @Test
    @DisplayName("Ao cadastrar um novo cliente, se tamanho de cpf for diferente de 11, retornar status code 400!")
    public void when_registerNewClient_and_clientCpfIsSmallerThenEleven_then_returnStatusCodeBadRequest() throws Exception {
        ClientDto clientDtoWithEmptyName = CLIENT_DTO;
        clientDtoWithEmptyName.setCpf("123");
        this.mockMvc
                .perform(
                        post("/v1/clientes/cadastrar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(clientDtoWithEmptyName))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"cpf\":\"não pode ficar em branco e deve conter exatamente 11 dígitos inteiros não negativos!\"}"));
    }

    @Test
    @DisplayName("Ao cadastrar um novo cliente, se cpf não for composto apenas por números, retornar status code 400!")
    public void when_registerNewClient_and_clientCpfHaveNotOnlyNumericDigits_then_returnStatusCodeBadRequest() throws Exception {
        ClientDto clientDtoWithEmptyName = CLIENT_DTO;
        clientDtoWithEmptyName.setCpf("123acb");
        this.mockMvc
                .perform(
                        post("/v1/clientes/cadastrar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(clientDtoWithEmptyName))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"cpf\":\"não pode ficar em branco e deve conter exatamente 11 dígitos inteiros não negativos!\"}"));
    }

    @Test
    @DisplayName("Ao cadastrar um novo cliente, se status for vazio, retornar status code 400!")
    public void when_registerNewClient_and_clientStatusIsEmpty_then_returnStatusCodeBadRequest() throws Exception {
        ClientDto clientDtoWithEmptyName = CLIENT_DTO;
        clientDtoWithEmptyName.setStatus(null);
        this.mockMvc
                .perform(
                        post("/v1/clientes/cadastrar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(clientDtoWithEmptyName))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":\"Precisa ser um status válido: [ATIVO, INATIVO]\"}"));
    }

    @Test
    @DisplayName("Ao alterar dados de um cliente com sucesso, retornar satatus code 201 e dados alterados!")
    public void when_successfullyUpdateClientData_then_returnStatusCode201AndUpdatedData() throws Exception {
        ClientDto clientDtoWithUpdatedData = ClientMockFactory.createClientDtoJoaoDaSilva();
        clientDtoWithUpdatedData.setName("José da Silva Xavier");
        clientDtoWithUpdatedData.setCpf("14741852963");
        Mockito.when(service.update(clientDtoWithUpdatedData,1L)).thenReturn(clientDtoWithUpdatedData);
        this.mockMvc
                .perform(
                        put("/v1/clientes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(clientDtoWithUpdatedData))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("José da Silva Xavier"));
    }

    @Test
    @DisplayName("Ao alterar dados de um cliente, se cliente não existir, retornar status code 404!")
    public void when_tryingToUpdateClient_and_clientDoesNotExist_then_returnStatusCode400() throws Exception {
        ClientDto clientDtoWithUpdatedData = ClientMockFactory.createClientDtoJoaoDaSilva();
        Mockito.when(service.update(clientDtoWithUpdatedData,1L)).thenThrow(new ClientNotFoundException("Cliente não encontrado!"));
        this.mockMvc
                .perform(
                        put("/v1/clientes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(clientDtoWithUpdatedData))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").value("Cliente não encontrado!"));
    }

    @Test
    @DisplayName("Ao buscar cliente pelo id, retornar status code 200 e dados do cliente!")
    public void when_getClientById_returnStatusCode200AndClientData() throws Exception {
        Mockito.when(service.findById(1L)).thenReturn(CLIENT_DTO);
        this.mockMvc
                .perform(
                        get("/v1/clientes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(CLIENT_DTO))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"João da Silva\",\"cpf\":\"78945612301\",\"status\":\"ATIVO\"}"));
    }

    @Test
    @DisplayName("Ao buscar cliente pelo id, se cliente não existir, retornar status code 404!")
    public void when_getClientById_and_clientDoesNotExists_then_returnStatusCode404() throws Exception {
        Mockito.when(service.findById(10L)).thenThrow(new ClientNotFoundException("Cliente não encontrado!"));
        this.mockMvc
                .perform(
                        get("/v1/clientes/10")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(CLIENT_DTO))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"id\":\"Cliente não encontrado!\"}"));
    }


    @Test
    @DisplayName("Ao buscar clientes ativos, retornar status code 200 e uma Page de clientDtos")
    public void when_getActiveClients_thenReturnStatusCode200AndPageOfClientDto() throws Exception {
        Page<ClientDto> page = ClientMockFactory.createPageOfThreeClientDtos();
        Mockito.when(service.findByStatus(Mockito.any())).thenReturn(page);
                this.mockMvc
                .perform(
                        get("/v1/clientes/ativos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Maria da Silva"));


    }

    @Test
    @DisplayName("Ao desabilitar cliente com sucesso, retornar status code 200 e ClientDto com status = 'INATIVO'")
    public void when_successfullyDisablingClient_then_returnStatusCode200AndClientDtoWithStatusEqualsInativo() throws Exception {
        ClientDto disabledClient = ClientMockFactory.createClientDtoJoaoDaSilva();
        disabledClient.setStatus(Status.INATIVO.name());
        Mockito.when(service.disableClient(1L)).thenReturn(disabledClient);
        this.mockMvc
                .perform(
                        patch("/v1/clientes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("INATIVO"));
    }

    @Test
    @DisplayName("Ao desabilitar cliente com sucesso, se cliente não existir, retornar status code 404")
    public void when_disablingClient_and_clientDoesNotExists_then_returnStatusCode200AndClientDtoWithStatusEqualsInativo() throws Exception {
        Mockito.when(service.disableClient(10L)).thenThrow(new ClientNotFoundException("Cliente não encontrado!"));
        this.mockMvc
                .perform(
                        patch("/v1/clientes/10")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").value("Cliente não encontrado!"));
    }
//    @Test
//    @DisplayName("Ao buscar clientes ativos, se não existe cliente cadastrado, retornar status code 404")
//    public void when_getActiveClients_and_thereIsNoRegisteredClient_thenReturnStatusCode404() throws Exception {
//        Page<ClientDto> page = ClientMockFactory.createPageOfThreeClientDtos();
//        Mockito.when(service.findByStatus(Mockito.any())).thenReturn(page);
//        this.mockMvc
//                .perform(
//                        get("/v1/clientes/ativos")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].name").value("Maria da Silva"));
//
//
//    }




}

