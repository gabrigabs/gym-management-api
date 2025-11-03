package br.edu.infnet.gabriel.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Instrutor no sistema.
 * Herda de Pessoa usando @MappedSuperclass.
 * Possui relacionamento OneToOne com Endereco.
 */
@Entity
@Table(name = "instrutores")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Instrutor extends Pessoa {
    @Column(nullable = false, unique = true)
    private String registro;
    
    private String especialidade;
    
    @Column(nullable = false)
    private Double salario;
    
    @Column(nullable = false)
    private Boolean status;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
}

