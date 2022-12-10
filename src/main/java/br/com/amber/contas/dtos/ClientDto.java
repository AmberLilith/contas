package br.com.amber.contas.dtos;

import br.com.amber.contas.utils.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ClientDto {
    @NotBlank(message = "não pode ficar em branco!")
    private String name;
    @NotBlank(message = "não pode ficar em branco!")
    @Length(min = 11, max = 11, message = "deve conter exatamente 11 dígitos inteiros não negativos!")
    @Pattern(regexp = "\\d+", message = "deve conter apenas números inteiros não negativos!")
    private String cpf;

    //private String endereco;
}
