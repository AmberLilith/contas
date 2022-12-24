package br.com.amber.contas.dtos;

import br.com.amber.contas.validations.annotations.IsValidStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {

    @IsValidStatus
    private String status;
}
