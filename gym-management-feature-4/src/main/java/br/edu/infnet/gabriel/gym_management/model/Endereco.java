package br.edu.infnet.gabriel.gym_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Endereco no sistema.
 */
@Entity
@Table(name = "enderecos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "CEP deve estar no formato XXXXX-XXX ou 8 dígitos")
    @Column(nullable = false)
    private String cep;
    
    @NotBlank(message = "Logradouro é obrigatório")
    @Size(min = 3, max = 200, message = "Logradouro deve ter entre 3 e 200 caracteres")
    @Column(nullable = false)
    private String logradouro;
    
    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
    private String complemento;
    
    @Size(max = 20, message = "Unidade deve ter no máximo 20 caracteres")
    private String unidade;
    
    @NotBlank(message = "Bairro é obrigatório")
    @Size(min = 3, max = 100, message = "Bairro deve ter entre 3 e 100 caracteres")
    private String bairro;
    
    @NotBlank(message = "Localidade é obrigatória")
    @Size(min = 3, max = 100, message = "Localidade deve ter entre 3 e 100 caracteres")
    private String localidade;
    
    @NotBlank(message = "UF é obrigatório")
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve ter 2 letras maiúsculas")
    private String uf;
    
    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 3, max = 50, message = "Estado deve ter entre 3 e 50 caracteres")
    private String estado;
}

