package br.edu.infnet.gabriel.gym_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Aluno no sistema.
 * Herda de Pessoa usando @MappedSuperclass.
 * Possui relacionamento ManyToOne com Academia.
 */
@Entity
@Table(name = "alunos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Aluno extends Pessoa {
    @NotBlank(message = "Matrícula é obrigatória")
    @Pattern(regexp = "MAT\\d{3,6}", message = "Matrícula deve estar no formato MATXXX (3-6 dígitos)")
    @Column(nullable = false, unique = true)
    private String matricula;
    
    @NotBlank(message = "Plano é obrigatório")
    @Size(min = 3, max = 50, message = "Plano deve ter entre 3 e 50 caracteres")
    private String plano;
    
    @NotBlank(message = "Data de início é obrigatória")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Data de início deve estar no formato YYYY-MM-DD")
    private String dataInicio;
    
    @NotNull(message = "Status é obrigatório")
    @Column(nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academia_id")
    @JsonBackReference("academia-alunos")
    private Academia academia;
}

