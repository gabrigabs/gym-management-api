package br.edu.infnet.gabriel.gym_management.service;

import br.edu.infnet.gabriel.gym_management.model.Academia;
import br.edu.infnet.gabriel.gym_management.repository.AcademiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pela gestão de Academias.
 * Utiliza JPA Repository para persistência de dados.
 * IDs são gerados automaticamente via JPA (IDENTITY strategy).
 */
@Service
public class AcademyService implements CrudService<Academia, Long> {

    private final AcademiaRepository academiaRepository;

    public AcademyService(AcademiaRepository academiaRepository) {
        this.academiaRepository = academiaRepository;
    }

    /**
     * Salva uma nova academia ou atualiza uma existente.
     *
     * @param academia A academia a ser salva
     * @return A academia salva com ID atribuído
     */
    @Override
    public Academia salvar(Academia academia) {
        return academiaRepository.save(academia);
    }

    /**
     * Busca uma academia pelo ID.
     *
     * @param id O ID da academia
     * @return A academia encontrada, ou null se não existir
     */
    @Override
    public Academia buscarPorId(Long id) {
        return academiaRepository.findById(id).orElse(null);
    }

    /**
     * Remove uma academia pelo ID.
     *
     * @param id O ID da academia a ser removida
     * @return true se a academia foi removida, false se não existia
     */
    @Override
    public Boolean excluir(Long id) {
        if (academiaRepository.existsById(id)) {
            academiaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Lista todas as academias armazenadas.
     *
     * @return Uma lista contendo todas as academias
     */
    @Override
    public List<Academia> listarTodos() {
        return academiaRepository.findAll();
    }
}

