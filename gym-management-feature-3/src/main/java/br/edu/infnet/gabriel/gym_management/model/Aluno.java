package br.edu.infnet.gabriel.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Aluno no sistema.
 * Herda de Pessoa usando @MappedSuperclass.
 */
@Entity
@Table(name = "alunos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Aluno extends Pessoa {
    @Column(nullable = false, unique = true)
    private String matricula;

    private String plano;
    private String dataInicio;

    @Column(nullable = false)
    private Boolean status;
}

