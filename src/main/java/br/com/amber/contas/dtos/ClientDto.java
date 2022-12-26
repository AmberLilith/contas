package br.com.amber.contas.dtos;

import br.com.amber.contas.models.Client;
import br.com.amber.contas.validations.annotations.IsValidStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ClientDto {
    @NotBlank(message = "não pode ficar em branco!")
    private String name;
    @Pattern(regexp = "\\d{11}+", message = "não pode ficar em branco e deve conter exatamente 11 dígitos inteiros não negativos!")
    private String cpf;
    @IsValidStatus()
    private String status;

    public ClientDto() {
    }

    public ClientDto(String name, String cpf, String status) {
        this.name = name;
        this.cpf = cpf;
        this.status = status;
    }

    public ClientDto(Client client) {
        this.cpf = client.getCpf();
        this.name = client.getName();
        this.status = client.getStatus();

    }

    public static Page<ClientDto> convertesToPage(Page<Client> clients){
        return clients.map(ClientDto::new);
    }
}
