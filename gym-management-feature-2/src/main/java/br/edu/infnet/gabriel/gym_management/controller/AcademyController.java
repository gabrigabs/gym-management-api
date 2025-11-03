package br.edu.infnet.gabriel.gym_management.controller;

import br.edu.infnet.gabriel.gym_management.model.Academia;
import br.edu.infnet.gabriel.gym_management.service.AcademyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável pelos endpoints relacionados a Academias.
 * Todos os endpoints utilizam o prefixo "/academias".
 */
@RestController
@RequestMapping("/academias")
public class AcademyController {

    private final AcademyService academyService;

    /**
     * Construtor com injeção de dependência via construtor.
     *
     * @param academyService O serviço de Academias
     */
    public AcademyController(AcademyService academyService) {
        this.academyService = academyService;
    }

    /**
     * Endpoint para listar todas as academias.
     *
     * GET /academias
     *
     * @return ResponseEntity contendo a lista de todas as academias
     */
    @GetMapping
    public ResponseEntity<List<Academia>> listarTodas() {
        List<Academia> academias = academyService.listarTodos();
        return ResponseEntity.ok(academias);
    }

    /**
     * Endpoint para buscar uma academia por ID.
     *
     * GET /academias/{id}
     *
     * @param id O ID da academia a ser buscada
     * @return ResponseEntity contendo a academia encontrada ou 404 se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<Academia> buscarPorId(@PathVariable Integer id) {
        Academia academia = academyService.buscarPorId(id);

        if (academia == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(academia);
    }

    /**
     * Endpoint para criar uma nova academia.
     *
     * POST /academias
     *
     * Request Body:
     * {
     *   "nome": "Academia Força Total",
     *   "cnpj": "12.345.678/0001-00",
     *   "endereco": "Rua A, 123",
     *   "telefone": "11987654321",
     *   "statusAtivo": true
     * }
     *
     * @param academia A academia a ser criada
     * @return ResponseEntity com status 201 (Created) e a academia salva
     */
    @PostMapping
    public ResponseEntity<Academia> criar(@RequestBody Academia academia) {
        Academia salva = academyService.salvar(academia);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    /**
     * Endpoint para atualizar uma academia existente.
     *
     * PUT /academias/{id}
     *
     * Request Body:
     * {
     *   "nome": "Academia Força Total Atualizada",
     *   "cnpj": "12.345.678/0001-00",
     *   "endereco": "Rua B, 456",
     *   "telefone": "11987654322",
     *   "statusAtivo": true
     * }
     *
     * @param id O ID da academia a ser atualizada
     * @param acadamiaAtualizada Os dados atualizados da academia
     * @return ResponseEntity com a academia atualizada ou 404 se não existir
     */
    @PutMapping("/{id}")
    public ResponseEntity<Academia> atualizar(@PathVariable Integer id, @RequestBody Academia acadamiaAtualizada) {
        Academia academia = academyService.buscarPorId(id);

        if (academia == null) {
            return ResponseEntity.notFound().build();
        }

        acadamiaAtualizada.setId(id);
        Academia salva = academyService.salvar(acadamiaAtualizada);
        return ResponseEntity.ok(salva);
    }

    /**
     * Endpoint para deletar uma academia.
     *
     * DELETE /academias/{id}
     *
     * @param id O ID da academia a ser deletada
     * @return ResponseEntity com status 204 (No Content) ou 404 se não existir
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        Boolean deletado = academyService.excluir(id);

        if (!deletado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

