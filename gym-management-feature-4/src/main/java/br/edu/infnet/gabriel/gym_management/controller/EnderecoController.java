package br.edu.infnet.gabriel.gym_management.controller;

import br.edu.infnet.gabriel.gym_management.model.Endereco;
import br.edu.infnet.gabriel.gym_management.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável pelos endpoints relacionados a Endereços.
 * Todos os endpoints utilizam o prefixo "/enderecos".
 * Exceções são tratadas globalmente pelo GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    /**
     * GET /enderecos
     * Lista todos os endereços
     */
    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos() {
        List<Endereco> enderecos = enderecoService.listarTodos();
        return ResponseEntity.ok(enderecos);
    }

    /**
     * GET /enderecos/{id}
     * Busca um endereço por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {
        Endereco endereco = enderecoService.buscarPorId(id);
        if (endereco == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(endereco);
    }

    /**
     * POST /enderecos
     * Cria um novo endereço
     */
    @PostMapping
    public ResponseEntity<Endereco> criar(@Valid @RequestBody Endereco endereco) {
        Endereco salvo = enderecoService.salvar(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * PUT /enderecos/{id}
     * Atualiza um endereço existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @Valid @RequestBody Endereco enderecoAtualizado) {
        Endereco endereco = enderecoService.buscarPorId(id);
        if (endereco == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        enderecoAtualizado.setId(id);
        Endereco salvo = enderecoService.salvar(enderecoAtualizado);
        return ResponseEntity.ok(salvo);
    }

    /**
     * DELETE /enderecos/{id}
     * Deleta um endereço
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Boolean deletado = enderecoService.excluir(id);
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

