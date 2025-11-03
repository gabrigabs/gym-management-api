package br.edu.infnet.gabriel.gym_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa uma Academia no sistema.
 * Contém informações essenciais como identificação, contato e status operacional.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Academia {

    private Integer id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;
    private Boolean statusAtivo;

    @Override
    public String toString() {
        return "Academia{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", statusAtivo=" + statusAtivo +
                '}';
    }
}

