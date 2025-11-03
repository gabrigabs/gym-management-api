package br.edu.infnet.gabriel.gym_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe abstrata que representa uma Pessoa no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pessoa {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
}

