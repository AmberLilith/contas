package br.com.amber.contas.controllers;

import br.com.amber.contas.dtos.AccountDto;
import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.enums.AccountType;
import br.com.amber.contas.exceptions.ClientNotFoundException;
import br.com.amber.contas.helpers.AccountMockFactory;
import br.com.amber.contas.helpers.ClientMockFactory;
import br.com.amber.contas.models.Account;
import br.com.amber.contas.services.AccountService;
import br.com.amber.contas.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ClientService clientService;

    ClientDto CLIENT_DTO = ClientMockFactory.createClientDtoJoaoDaSilva();
    AccountDto ACCOUNT_DTO = AccountMockFactory.createCurrentAccountDto();

    @Test
    @DisplayName("Ao cadastrar uma nova conta com sucesso, retornar status code 201 e dados da conta cadastrada!")
    public void when_registerNewAccount_then_returnStatusCodeCreated() throws Exception {
        Mockito.when(accountService.save(Mockito.any(Account.class))).thenReturn(ACCOUNT_DTO);
        this.mockMvc
               .perform(
                        post("/v1/contas/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(ACCOUNT_DTO))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isCreated())
               .andExpect(jsonPath("$.type").value(AccountType.CORRENTE.name()));
}

    @Test //TODO aqui está certo mocar accountService.save invés de accountService.findById?
    @DisplayName("Ao cadastrar uma nova conta e cliente não exitir, lançar ClientNotFoundException!")
    public void when_registerNewAccount_and_clientDoesNotExist_then_trhowClientNotFoundException() throws Exception {
        String exceptionMessage = String.format("Cliente com %d não encontrado!", 1L);
        Mockito.when(accountService.save(Mockito.any(Account.class))).thenThrow(new ClientNotFoundException(String.format(exceptionMessage)));
        this.mockMvc
                .perform(
                        post("/v1/contas/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(ACCOUNT_DTO))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").value(exceptionMessage));
    }

    @Test
    @DisplayName("Ao cadastrar uma nova conta e status for inválido, retornar status code 400")
    public void when_registerNewAccount_and_statusIsInvalid_then_returnStatusCode400() throws Exception {
        AccountDto accountDtoWithInvalidStatus = ACCOUNT_DTO;
        accountDtoWithInvalidStatus.setStatus("invalido");
        Mockito.when(accountService.save(Mockito.any(Account.class))).thenReturn(accountDtoWithInvalidStatus);
        this.mockMvc
                .perform(
                        post("/v1/contas/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(ACCOUNT_DTO))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Precisa ser um status válido: [ATIVO, INATIVO]"));
    }

    @Test
    @DisplayName("Ao cadastrar uma nova conta e type for inválido, retornar status code 400")
    public void when_registerNewAccount_and_typeIsInvalid_then_returnStatusCode400() throws Exception {
        AccountDto accountDtoWithInvalidStatus = ACCOUNT_DTO;
        accountDtoWithInvalidStatus.setType("invalido");
        Mockito.when(accountService.save(Mockito.any(Account.class))).thenReturn(accountDtoWithInvalidStatus);
        this.mockMvc
                .perform(
                        post("/v1/contas/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(ACCOUNT_DTO))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Precisa ser um tipo de conta válido: [CORRENTE, POUPANCA, SALARIO]"));
    }

    @Test
    @DisplayName("Ao cadastrar uma nova conta e balance for nulo, retornar status code 400")
    public void when_registerNewAccount_and_balanceIsNull_then_returnStatusCode400() throws Exception {
        AccountDto accountDtoWithInvalidStatus = ACCOUNT_DTO;
        accountDtoWithInvalidStatus.setBalance(null);
        Mockito.when(accountService.save(Mockito.any(Account.class))).thenReturn(accountDtoWithInvalidStatus);
        this.mockMvc
                .perform(
                        post("/v1/contas/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(ACCOUNT_DTO))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.balance").value("não pode ficar em branco nem ser nulo!"));
    }

    @Test
    @DisplayName("Ao cadastrar uma nova conta e clientId for nulo, retornar status code 400")
    public void when_registerNewAccount_and_clientIdIsNull_then_returnStatusCode400() throws Exception {
        AccountDto accountDtoWithInvalidStatus = ACCOUNT_DTO;
        accountDtoWithInvalidStatus.setClientId(null);
        Mockito.when(accountService.save(Mockito.any(Account.class))).thenReturn(accountDtoWithInvalidStatus);
        this.mockMvc
                .perform(
                        post("/v1/contas/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(ACCOUNT_DTO))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.clientId").value("não pode ficar em branco nem ser nulo!"));
    }


    @Test
    @DisplayName("Ao buscar as contas de um cliente, retornar status code 200 e lista de contas")
    public void when_findAccountByIdClient_then_returnStatusCode200AndListOfAccount() throws Exception {
        List<AccountDto> accountDtoList = AccountMockFactory.createAListOfThreeAccountDtos();
        Mockito.when(accountService.findByClientId(1L)).thenReturn(accountDtoList);
        this.mockMvc
                .perform(
                        get("/v1/contas/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(ACCOUNT_DTO))
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].type").value(AccountType.POUPANCA.name()));
    }

}
