package br.edu.infnet.gabriel.gym_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Instrutor no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instrutor extends Pessoa {
    private String registro;
    private String especialidade;
    private Double salario;
    private Boolean status;
    private Endereco endereco;
}

