package br.edu.infnet.gabriel.gym_management.loader;

import br.edu.infnet.gabriel.gym_management.model.Academia;
import br.edu.infnet.gabriel.gym_management.service.AcademyService;
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
 * Componente que carrega academias de um arquivo texto na inicialização da aplicação.
 * O arquivo esperado é "academias.txt" no classpath (resources).
 *
 * Formato do arquivo:
 * nome;cnpj;endereco;telefone;statusAtivo
 *
 * Exemplo:
 * Academia Força Total;12.345.678/0001-00;Rua A, 123;11987654321;true
 */
@Component
public class AcademyLoader implements ApplicationRunner {

    private final AcademyService academyService;

    public AcademyLoader(AcademyService academyService) {
        this.academyService = academyService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Academia> academiasCarregadas = carregarAcademias();
        imprimirAcademias(academiasCarregadas);
    }

    /**
     * Carrega academias do arquivo "academias.txt".
     *
     * @return Lista de academias carregadas
     */
    private List<Academia> carregarAcademias() {
        List<Academia> academias = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource("academias.txt");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String linha;
                while ((linha = reader.readLine()) != null) {
                    linha = linha.trim();

                    // Ignora linhas vazias e comentários
                    if (linha.isEmpty() || linha.startsWith("#")) {
                        continue;
                    }

                    Academia academia = parsearLinha(linha);
                    if (academia != null) {
                        Academia salva = academyService.salvar(academia);
                        academias.add(salva);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo academias.txt: " + e.getMessage());
        }

        return academias;
    }

    /**
     * Converte uma linha do arquivo em um objeto Academia.
     * Formato esperado: nome;cnpj;endereco;telefone;statusAtivo
     *
     * @param linha A linha a ser parseada
     * @return Um objeto Academia ou null se houver erro na formatação
     */
    private Academia parsearLinha(String linha) {
        try {
            String[] partes = linha.split(";");

            if (partes.length != 5) {
                System.err.println("Formato inválido na linha: " + linha);
                return null;
            }

            Academia academia = new Academia();
            academia.setNome(partes[0].trim());
            academia.setCnpj(partes[1].trim());
            academia.setEndereco(partes[2].trim());
            academia.setTelefone(partes[3].trim());
            academia.setStatusAtivo(Boolean.parseBoolean(partes[4].trim()));

            return academia;
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha: " + linha + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Imprime no console todas as academias carregadas.
     *
     * @param academias Lista de academias a serem impressas
     */
    private void imprimirAcademias(List<Academia> academias) {
        System.out.println("\n========================================");
        System.out.println("ACADEMIAS CARREGADAS NA INICIALIZAÇÃO");
        System.out.println("========================================");

        if (academias.isEmpty()) {
            System.out.println("Nenhuma academia foi carregada.");
        } else {
            for (Academia academia : academias) {
                System.out.println(academia);
            }
            System.out.println("Total: " + academias.size() + " academia(s) carregada(s)");
        }

        System.out.println("========================================\n");
    }
}

