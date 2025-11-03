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
     * POST /academias
     * Cria uma nova academia
     */
    @PostMapping
    public ResponseEntity<Academia> criar(@RequestBody Academia academia) {
        Academia salva = academyService.salvar(academia);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    /**
     * PUT /academias/{id}
     * Atualiza uma academia existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Academia> atualizar(@PathVariable Long id, @RequestBody Academia academiaAtualizada) {
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

