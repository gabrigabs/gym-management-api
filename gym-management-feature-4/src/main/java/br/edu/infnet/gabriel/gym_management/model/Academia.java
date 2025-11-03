package br.edu.infnet.gabriel.gym_management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma Academia no sistema.
 * Contém informações essenciais como identificação, contato e status operacional.
 * Possui relacionamento OneToMany com Instrutores e Alunos.
 */
@Entity
@Table(name = "academias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Academia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String nome;
    
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX")
    @Column(nullable = false, unique = true)
    private String cnpj;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Size(min = 10, max = 200, message = "Endereço deve ter entre 10 e 200 caracteres")
    @Column(nullable = false)
    private String endereco;
    
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX")
    private String telefone;
    
    @NotNull(message = "Status ativo é obrigatório")
    @Column(nullable = false)
    private Boolean statusAtivo;

    @OneToMany(mappedBy = "academia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("academia-instrutores")
    private List<Instrutor> instrutores = new ArrayList<>();

    @OneToMany(mappedBy = "academia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("academia-alunos")
    private List<Aluno> alunos = new ArrayList<>();

    /**
     * Adiciona um instrutor à academia
     */
    public void adicionarInstrutor(Instrutor instrutor) {
        instrutores.add(instrutor);
        instrutor.setAcademia(this);
    }

    /**
     * Remove um instrutor da academia
     */
    public void removerInstrutor(Instrutor instrutor) {
        instrutores.remove(instrutor);
        instrutor.setAcademia(null);
    }

    /**
     * Adiciona um aluno à academia
     */
    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
        aluno.setAcademia(this);
    }

    /**
     * Remove um aluno da academia
     */
    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
        aluno.setAcademia(null);
    }

    @Override
    public String toString() {
        return "Academia{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", statusAtivo=" + statusAtivo +
                ", instrutoresCount=" + (instrutores != null ? instrutores.size() : 0) +
                ", alunosCount=" + (alunos != null ? alunos.size() : 0) +
                '}';
    }
}

