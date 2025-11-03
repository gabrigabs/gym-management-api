package br.edu.infnet.gabriel.gym_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Endereco no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private Integer id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
}

