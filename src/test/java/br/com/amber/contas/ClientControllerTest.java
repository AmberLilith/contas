package br.com.amber.contas;

import br.com.amber.contas.controllers.ClientController;
import br.com.amber.contas.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository repository;

    @Test
    public void teste() throws Exception {
        this.mockMvc
                .perform(
                        post("/clientes/cadastrar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"name\":\"Amber\",\"cpf\":\"78945612347\"}")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"Amber\",\"cpf\":\"78945612347\"}"));
    }
}
