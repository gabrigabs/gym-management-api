package br.edu.infnet.gabriel.gym_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Instrutor no sistema.
 * Herda de Pessoa usando @MappedSuperclass.
 * Possui relacionamento OneToOne com Endereco e ManyToOne com Academia.
 */
@Entity
@Table(name = "instrutores")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Instrutor extends Pessoa {
    @NotBlank(message = "Registro é obrigatório")
    @Pattern(regexp = "REG\\d{3,6}", message = "Registro deve estar no formato REGXXX (3-6 dígitos)")
    @Column(nullable = false, unique = true)
    private String registro;
    
    @NotBlank(message = "Especialidade é obrigatória")
    @Size(min = 3, max = 50, message = "Especialidade deve ter entre 3 e 50 caracteres")
    private String especialidade;
    
    @NotNull(message = "Salário é obrigatório")
    @Min(value = 1320, message = "Salário deve ser no mínimo R$ 1.320,00 (salário mínimo)")
    @Column(nullable = false)
    private Double salario;
    
    @NotNull(message = "Status é obrigatório")
    @Column(nullable = false)
    private Boolean status;
    
    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academia_id")
    @JsonBackReference("academia-instrutores")
    private Academia academia;
}

