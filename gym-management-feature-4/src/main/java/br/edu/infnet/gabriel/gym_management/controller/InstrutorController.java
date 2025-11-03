package br.edu.infnet.gabriel.gym_management.controller;

import br.edu.infnet.gabriel.gym_management.model.Instrutor;
import br.edu.infnet.gabriel.gym_management.service.InstrutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST responsável pelos endpoints relacionados a Instrutores.
 * Todos os endpoints utilizam o prefixo "/instrutores".
 * Exceções são tratadas globalmente pelo GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    @GetMapping
    public ResponseEntity<List<Instrutor>> listarTodos() {
        List<Instrutor> instrutores = instrutorService.listarTodos();
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrutor> buscarPorId(@PathVariable Long id) {
        Instrutor instrutor = instrutorService.buscarPorId(id);
        return ResponseEntity.ok(instrutor);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Instrutor> buscarPorCpf(@PathVariable String cpf) {
        Instrutor instrutor = instrutorService.buscarPorCpf(cpf);
        return ResponseEntity.ok(instrutor);
    }

    @GetMapping("/registro/{registro}")
    public ResponseEntity<Instrutor> buscarPorRegistro(@PathVariable String registro) {
        Instrutor instrutor = instrutorService.buscarPorRegistro(registro);
        return ResponseEntity.ok(instrutor);
    }

    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<Instrutor>> buscarPorEspecialidade(@PathVariable String especialidade) {
        List<Instrutor> instrutores = instrutorService.buscarPorEspecialidade(especialidade);
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Instrutor>> buscarPorStatus(@PathVariable Boolean status) {
        List<Instrutor> instrutores = instrutorService.buscarPorStatus(status);
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/especialidade/{especialidade}/status/{status}")
    public ResponseEntity<List<Instrutor>> buscarPorEspecialidadeEStatus(@PathVariable String especialidade, @PathVariable Boolean status) {
        List<Instrutor> instrutores = instrutorService.buscarPorEspecialidadeEStatus(especialidade, status);
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/salario")
    public ResponseEntity<List<Instrutor>> buscarPorFaixaSalario(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<Instrutor> instrutores = instrutorService.buscarPorFaixaSalario(min, max);
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/salario-acima/{valor}")
    public ResponseEntity<List<Instrutor>> buscarComSalarioAcima(@PathVariable Double valor) {
        List<Instrutor> instrutores = instrutorService.buscarComSalarioAcima(valor);
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/academia/{academiaId}")
    public ResponseEntity<List<Instrutor>> buscarPorAcademia(@PathVariable Long academiaId) {
        List<Instrutor> instrutores = instrutorService.buscarPorAcademia(academiaId);
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/academia/{academiaId}/ativos")
    public ResponseEntity<List<Instrutor>> buscarAtivosDeAcademia(@PathVariable Long academiaId) {
        List<Instrutor> instrutores = instrutorService.buscarInstrutoresAtivosDeAcademia(academiaId);
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/sem-academia")
    public ResponseEntity<List<Instrutor>> buscarSemAcademia() {
        List<Instrutor> instrutores = instrutorService.buscarSemAcademia();
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<Instrutor>> buscarPorCidade(@PathVariable String cidade) {
        List<Instrutor> instrutores = instrutorService.buscarPorCidade(cidade);
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Long>> obterEstatisticas() {
        Map<String, Long> estatisticas = instrutorService.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }

    @PostMapping
    public ResponseEntity<Instrutor> criar(@Valid @RequestBody Instrutor instrutor) {
        Instrutor salvo = instrutorService.salvar(instrutor);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instrutor> atualizar(@PathVariable Long id, @Valid @RequestBody Instrutor instrutorAtualizado) {
        Instrutor instrutor = instrutorService.buscarPorId(id);
        instrutorAtualizado.setId(id);
        Instrutor salvo = instrutorService.salvar(instrutorAtualizado);
        return ResponseEntity.ok(salvo);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Instrutor> inativar(@PathVariable Long id) {
        Instrutor instrutor = instrutorService.inativar(id);
        return ResponseEntity.ok(instrutor);
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Instrutor> ativar(@PathVariable Long id) {
        Instrutor instrutor = instrutorService.ativar(id);
        return ResponseEntity.ok(instrutor);
    }

    @PatchMapping("/{id}/vincular-academia/{academiaId}")
    public ResponseEntity<Instrutor> vincularAcademia(@PathVariable Long id, @PathVariable Long academiaId) {
        Instrutor instrutor = instrutorService.vincularAcademia(id, academiaId);
        return ResponseEntity.ok(instrutor);
    }

    @PatchMapping("/{id}/desvincular-academia")
    public ResponseEntity<Instrutor> desvincularAcademia(@PathVariable Long id) {
        Instrutor instrutor = instrutorService.desvincularAcademia(id);
        return ResponseEntity.ok(instrutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Boolean deletado = instrutorService.excluir(id);
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

