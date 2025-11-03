package br.edu.infnet.gabriel.gym_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Aluno no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno extends Pessoa {
    private String matricula;
    private String plano;
    private String dataInicio;
    private Boolean status;
}

