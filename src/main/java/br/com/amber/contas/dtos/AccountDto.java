package br.com.amber.contas.dtos;

import br.com.amber.contas.validations.annotations.IsValidAccountType;
import br.com.amber.contas.validations.annotations.IsValidStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @IsValidStatus
    private String status;

    //@NotBlank(message = "não pode ficar em branco!")
    private Double balance;

    //@NotBlank(message = "não pode ficar em branco!")
    private Long clientId;

    //@IsValidAccountType
    private String type;
}
