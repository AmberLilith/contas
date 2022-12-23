package br.com.amber.contas.models;

import br.com.amber.contas.Status;
import br.com.amber.contas.utils.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"cpf"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private String status;

    //private String endereco;
}
