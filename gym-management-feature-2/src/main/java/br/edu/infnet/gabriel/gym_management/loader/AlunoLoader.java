package br.edu.infnet.gabriel.gym_management.loader;

import br.edu.infnet.gabriel.gym_management.model.Aluno;
import br.edu.infnet.gabriel.gym_management.service.AlunoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Componente que carrega alunos de um arquivo texto na inicialização da aplicação.
 * O arquivo esperado é "alunos.txt" no classpath (resources).
 *
 * Formato do arquivo:
 * nome;email;cpf;telefone;matricula;plano;dataInicio;status
 *
 * Exemplo:
 * Maria Santos;maria@gmail.com;987.654.321-11;11988888888;MAT001;Gold;2024-01-15;true
 */
@Component
public class AlunoLoader implements ApplicationRunner {

    private final AlunoService alunoService;

    public AlunoLoader(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Aluno> alunosCarregados = carregarAlunos();
        imprimirAlunos(alunosCarregados);
    }

    /**
     * Carrega alunos do arquivo "alunos.txt".
     *
     * @return Lista de alunos carregados
     */
    private List<Aluno> carregarAlunos() {
        List<Aluno> alunos = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource("alunos.txt");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String linha;
                while ((linha = reader.readLine()) != null) {
                    linha = linha.trim();

                    // Ignora linhas vazias e comentários
                    if (linha.isEmpty() || linha.startsWith("#")) {
                        continue;
                    }

                    Aluno aluno = parsearLinha(linha);
                    if (aluno != null) {
                        Aluno salvo = alunoService.salvar(aluno);
                        alunos.add(salvo);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo alunos.txt: " + e.getMessage());
        }

        return alunos;
    }

    /**
     * Converte uma linha do arquivo em um objeto Aluno.
     * Formato esperado: nome;email;cpf;telefone;matricula;plano;dataInicio;status
     *
     * @param linha A linha a ser parseada
     * @return Um objeto Aluno ou null se houver erro na formatação
     */
    private Aluno parsearLinha(String linha) {
        try {
            String[] partes = linha.split(";");

            if (partes.length != 8) {
                System.err.println("Formato inválido na linha: " + linha);
                return null;
            }

            Aluno aluno = new Aluno();
            aluno.setNome(partes[0].trim());
            aluno.setEmail(partes[1].trim());
            aluno.setCpf(partes[2].trim());
            aluno.setTelefone(partes[3].trim());
            aluno.setMatricula(partes[4].trim());
            aluno.setPlano(partes[5].trim());
            aluno.setDataInicio(partes[6].trim());
            aluno.setStatus(Boolean.parseBoolean(partes[7].trim()));

            return aluno;

        } catch (Exception e) {
            System.err.println("Erro ao parsear linha do aluno: " + linha + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Imprime os alunos carregados no console.
     */
    private void imprimirAlunos(List<Aluno> alunos) {
        System.out.println("\n========== ALUNOS CARREGADOS ==========");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno foi carregado.");
        } else {
            alunos.forEach(a -> System.out.println(a));
        }
        System.out.println("====================================\n");
    }
}

