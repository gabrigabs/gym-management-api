package br.edu.infnet.gabriel.gym_management.controller;

import br.edu.infnet.gabriel.gym_management.model.Endereco;
import br.edu.infnet.gabriel.gym_management.service.EnderecoService;
import br.edu.infnet.gabriel.gym_management.exception.EnderecoInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável pelos endpoints relacionados a Endereços.
 * Todos os endpoints utilizam o prefixo "/enderecos".
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
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Integer id) {
        try {
            Endereco endereco = enderecoService.buscarPorId(id);
            return ResponseEntity.ok(endereco);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /enderecos
     * Cria um novo endereço
     */
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Endereco endereco) {
        try {
            Endereco salvo = enderecoService.salvar(endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (EnderecoInvalidoException e) {
            return ResponseEntity.badRequest().body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * PUT /enderecos/{id}
     * Atualiza um endereço existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Endereco enderecoAtualizado) {
        try {
            Endereco endereco = enderecoService.buscarPorId(id);
            if (endereco == null) {
                return ResponseEntity.notFound().build();
            }
            enderecoAtualizado.setId(id);
            Endereco salvo = enderecoService.salvar(enderecoAtualizado);
            return ResponseEntity.ok(salvo);
        } catch (EnderecoInvalidoException e) {
            return ResponseEntity.badRequest().body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * DELETE /enderecos/{id}
     * Deleta um endereço
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        Boolean deletado = enderecoService.excluir(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

