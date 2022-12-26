package br.com.amber.contas.controllers;

import br.com.amber.contas.dtos.AccountDto;
import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.enums.AccountType;
import br.com.amber.contas.exceptions.ClientNotFoundException;
import br.com.amber.contas.helpers.AccountMockFactory;
import br.com.amber.contas.helpers.ClientMockFactory;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

//    @MockBean
//    private ClientService clientService;

    ClientDto CLIENT_DTO = ClientMockFactory.createClientDtoJoaoDaSilva();
    AccountDto ACCOUNT_DTO = AccountMockFactory.createCurrentAccountDto();

    @Test
    @DisplayName("Ao cadastrar uma nova conta com sucesso, retornar status code 201 e dados da conta cadastrada!")
    public void when_registerNewAccount_then_returnStatusCodeCreated() throws Exception {
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

//    @Test
//    @DisplayName("Ao cadastrar uma nova conta e cliente não exitir, lançar ClientNotFoundException!")
//    public void when_registerNewAccount_and_clientDoesNotExist_then_trhowClientNotFoundException() throws Exception {
//        Mockito.when(acco.findById(1L)).thenThrow(new ClientNotFoundException(String.format("Cliente com %d não encontrado!", 1L));
//        this.mockMvc
//                .perform(
//                        post("/v1/contas/")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(new ObjectMapper().writeValueAsString(ACCOUNT_DTO))
//                                .accept(MediaType.APPLICATION_JSON)
//
//                )
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.type").value(AccountType.CORRENTE.name()));
//    }
}
