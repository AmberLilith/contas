package br.com.amber.contas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ClientPatchDto {
    @NotBlank(message = "não pode ficar em branco!")
    private String name;

}
