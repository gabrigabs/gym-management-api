package br.edu.infnet.gabriel.gym_management.loader;

import br.edu.infnet.gabriel.gym_management.model.Instrutor;
import br.edu.infnet.gabriel.gym_management.model.Endereco;
import br.edu.infnet.gabriel.gym_management.service.InstrutorService;
import br.edu.infnet.gabriel.gym_management.service.EnderecoService;
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
 * Componente que carrega instrutores e seus endereços de um arquivo texto na inicialização da aplicação.
 * O arquivo esperado é "instrutores.txt" no classpath (resources).
 *
 * Formato do arquivo:
 * nome;email;cpf;telefone;registro;especialidade;salario;status;cep;logradouro;complemento;unidade;bairro;localidade;uf;estado
 *
 * Exemplo:
 * João Silva;joao@gmail.com;123.456.789-10;11999999999;REG001;Musculação;5000.00;true;01310-100;Av. Paulista;Apto 100;100;Centro;São Paulo;SP;São Paulo
 */
@Component
public class InstrutorLoader implements ApplicationRunner {

    private final InstrutorService instrutorService;
    private final EnderecoService enderecoService;

    public InstrutorLoader(InstrutorService instrutorService, EnderecoService enderecoService) {
        this.instrutorService = instrutorService;
        this.enderecoService = enderecoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Instrutor> instrutoresCarregados = carregarInstrutores();
        imprimirInstrutores(instrutoresCarregados);
    }

    /**
     * Carrega instrutores do arquivo "instrutores.txt".
     *
     * @return Lista de instrutores carregados
     */
    private List<Instrutor> carregarInstrutores() {
        List<Instrutor> instrutores = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource("instrutores.txt");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String linha;
                while ((linha = reader.readLine()) != null) {
                    linha = linha.trim();

                    // Ignora linhas vazias e comentários
                    if (linha.isEmpty() || linha.startsWith("#")) {
                        continue;
                    }

                    Instrutor instrutor = parsearLinha(linha);
                    if (instrutor != null) {
                        Instrutor salvo = instrutorService.salvar(instrutor);
                        instrutores.add(salvo);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo instrutores.txt: " + e.getMessage());
        }

        return instrutores;
    }

    /**
     * Converte uma linha do arquivo em um objeto Instrutor com Endereco.
     * Formato esperado: nome;email;cpf;telefone;registro;especialidade;salario;status;cep;logradouro;complemento;unidade;bairro;localidade;uf;estado
     *
     * @param linha A linha a ser parseada
     * @return Um objeto Instrutor ou null se houver erro na formatação
     */
    private Instrutor parsearLinha(String linha) {
        try {
            String[] partes = linha.split(";");

            if (partes.length != 16) {
                System.err.println("Formato inválido na linha: " + linha);
                return null;
            }

            // Criar endereço
            Endereco endereco = new Endereco();
            endereco.setCep(partes[8].trim());
            endereco.setLogradouro(partes[9].trim());
            endereco.setComplemento(partes[10].trim());
            endereco.setUnidade(partes[11].trim());
            endereco.setBairro(partes[12].trim());
            endereco.setLocalidade(partes[13].trim());
            endereco.setUf(partes[14].trim());
            endereco.setEstado(partes[15].trim());

            // Salvar endereço
            Endereco enderecoSalvo = enderecoService.salvar(endereco);

            // Criar instrutor
            Instrutor instrutor = new Instrutor();
            instrutor.setNome(partes[0].trim());
            instrutor.setEmail(partes[1].trim());
            instrutor.setCpf(partes[2].trim());
            instrutor.setTelefone(partes[3].trim());
            instrutor.setRegistro(partes[4].trim());
            instrutor.setEspecialidade(partes[5].trim());
            instrutor.setSalario(Double.parseDouble(partes[6].trim()));
            instrutor.setStatus(Boolean.parseBoolean(partes[7].trim()));
            instrutor.setEndereco(enderecoSalvo);

            return instrutor;

        } catch (Exception e) {
            System.err.println("Erro ao parsear linha do instrutor: " + linha + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Imprime os instrutores carregados no console.
     */
    private void imprimirInstrutores(List<Instrutor> instrutores) {
        System.out.println("\n========== INSTRUTORES CARREGADOS ==========");
        if (instrutores.isEmpty()) {
            System.out.println("Nenhum instrutor foi carregado.");
        } else {
            instrutores.forEach(i -> System.out.println(i));
        }
        System.out.println("==========================================\n");
    }
}

