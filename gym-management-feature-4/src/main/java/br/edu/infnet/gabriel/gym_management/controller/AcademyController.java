package br.edu.infnet.gabriel.gym_management.controller;

import br.edu.infnet.gabriel.gym_management.model.Academia;
import br.edu.infnet.gabriel.gym_management.service.AcademyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST responsável pelos endpoints relacionados a Academias.
 * Todos os endpoints utilizam o prefixo "/academias".
 */
@RestController
@RequestMapping("/academias")
public class AcademyController {

    private final AcademyService academyService;

    public AcademyController(AcademyService academyService) {
        this.academyService = academyService;
    }

    /**
     * GET /academias
     * Lista todas as academias
     */
    @GetMapping
    public ResponseEntity<List<Academia>> listarTodas() {
        List<Academia> academias = academyService.listarTodos();
        return ResponseEntity.ok(academias);
    }

    /**
     * GET /academias/{id}
     * Busca uma academia por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Academia> buscarPorId(@PathVariable Long id) {
        Academia academia = academyService.buscarPorId(id);
        if (academia == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(academia);
    }

    /**
     * GET /academias/status/{status}
     * Busca academias por status (ativo/inativo)
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Academia>> buscarPorStatus(@PathVariable Boolean status) {
        List<Academia> academias = academyService.buscarPorStatus(status);
        return ResponseEntity.ok(academias);
    }

    /**
     * GET /academias/buscar
     * Busca academias por nome (query parameter)
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Academia>> buscarPorNome(@RequestParam String nome) {
        List<Academia> academias = academyService.buscarPorNome(nome);
        return ResponseEntity.ok(academias);
    }

    /**
     * GET /academias/ativas-com-instrutores
     * Lista academias ativas que possuem instrutores
     */
    @GetMapping("/ativas-com-instrutores")
    public ResponseEntity<List<Academia>> listarAtivasComInstrutores() {
        List<Academia> academias = academyService.buscarAcademiasAtivasComInstrutores();
        return ResponseEntity.ok(academias);
    }

    /**
     * GET /academias/minimo-alunos/{quantidade}
     * Busca academias com pelo menos X alunos
     */
    @GetMapping("/minimo-alunos/{quantidade}")
    public ResponseEntity<List<Academia>> buscarComMinimoAlunos(@PathVariable int quantidade) {
        List<Academia> academias = academyService.buscarAcademiasComMinimoAlunos(quantidade);
        return ResponseEntity.ok(academias);
    }

    /**
     * GET /academias/estatisticas
     * Retorna estatísticas sobre academias
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Long>> obterEstatisticas() {
        Map<String, Long> estatisticas = academyService.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }

    /**
     * POST /academias
     * Cria uma nova academia
     */
    @PostMapping
    public ResponseEntity<Academia> criar(@Valid @RequestBody Academia academia) {
        Academia salva = academyService.salvar(academia);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    /**
     * PUT /academias/{id}
     * Atualiza uma academia existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Academia> atualizar(@PathVariable Long id, @Valid @RequestBody Academia academiaAtualizada) {
        Academia academia = academyService.buscarPorId(id);
        if (academia == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        academiaAtualizada.setId(id);
        Academia salva = academyService.salvar(academiaAtualizada);
        return ResponseEntity.ok(salva);
    }

    /**
     * DELETE /academias/{id}
     * Deleta uma academia
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Boolean deletado = academyService.excluir(id);
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

