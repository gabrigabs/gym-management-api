package br.edu.infnet.gabriel.gym_management.controller;

import br.edu.infnet.gabriel.gym_management.model.Instrutor;
import br.edu.infnet.gabriel.gym_management.service.InstrutorService;
import br.edu.infnet.gabriel.gym_management.exception.InstrutorInvalidoException;
import br.edu.infnet.gabriel.gym_management.exception.InstrutorNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável pelos endpoints relacionados a Instrutores.
 * Todos os endpoints utilizam o prefixo "/instrutores".
 */
@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    /**
     * GET /instrutores
     * Lista todos os instrutores
     */
    @GetMapping
    public ResponseEntity<List<Instrutor>> listarTodos() {
        List<Instrutor> instrutores = instrutorService.listarTodos();
        return ResponseEntity.ok(instrutores);
    }

    /**
     * GET /instrutores/{id}
     * Busca um instrutor por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Instrutor instrutor = instrutorService.buscarPorId(id);
            return ResponseEntity.ok(instrutor);
        } catch (InstrutorNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * GET /instrutores/cpf/{cpf}
     * Busca um instrutor por CPF
     */
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
        try {
            Instrutor instrutor = instrutorService.buscarPorCpf(cpf);
            return ResponseEntity.ok(instrutor);
        } catch (InstrutorNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * GET /instrutores/especialidade/{especialidade}
     * Busca instrutores por especialidade
     */
    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<Instrutor>> buscarPorEspecialidade(@PathVariable String especialidade) {
        List<Instrutor> instrutores = instrutorService.buscarPorEspecialidade(especialidade);
        return ResponseEntity.ok(instrutores);
    }

    /**
     * POST /instrutores
     * Cria um novo instrutor
     */
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Instrutor instrutor) {
        try {
            Instrutor salvo = instrutorService.salvar(instrutor);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (InstrutorInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * PUT /instrutores/{id}
     * Atualiza um instrutor existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Instrutor instrutorAtualizado) {
        try {
            Instrutor instrutor = instrutorService.buscarPorId(id);
            instrutorAtualizado.setId(id);
            Instrutor salvo = instrutorService.salvar(instrutorAtualizado);
            return ResponseEntity.ok(salvo);
        } catch (InstrutorInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        } catch (InstrutorNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * PATCH /instrutores/{id}/inativar
     * Inativa um instrutor (altera status para false)
     */
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            Instrutor instrutor = instrutorService.inativar(id);
            return ResponseEntity.ok(instrutor);
        } catch (InstrutorNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * PATCH /instrutores/{id}/ativar
     * Ativa um instrutor (altera status para true)
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long id) {
        try {
            Instrutor instrutor = instrutorService.ativar(id);
            return ResponseEntity.ok(instrutor);
        } catch (InstrutorNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * DELETE /instrutores/{id}
     * Deleta um instrutor
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Boolean deletado = instrutorService.excluir(id);
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

