package br.com.amber.contas.models;

import br.com.amber.contas.utils.Endereco;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"cpf"})})
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    //private String endereco;
}
