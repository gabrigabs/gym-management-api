package br.edu.infnet.gabriel.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe abstrata que representa uma Pessoa no sistema.
 * Utiliza @MappedSuperclass para herança JPA - os atributos serão mapeados nas tabelas das subclasses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true)
    private String cpf;
    
    private String telefone;
}

