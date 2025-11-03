package br.edu.infnet.gabriel.gym_management.controller;

import br.edu.infnet.gabriel.gym_management.model.Aluno;
import br.edu.infnet.gabriel.gym_management.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST responsável pelos endpoints relacionados a Alunos.
 * Todos os endpoints utilizam o prefixo "/alunos".
 * Exceções são tratadas globalmente pelo GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    /**
     * GET /alunos
     * Lista todos os alunos
     */
    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        List<Aluno> alunos = alunoService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    /**
     * GET /alunos/{id}
     * Busca um aluno por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    /**
     * GET /alunos/cpf/{cpf}
     * Busca um aluno por CPF
     */
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Aluno> buscarPorCpf(@PathVariable String cpf) {
        Aluno aluno = alunoService.buscarPorCpf(cpf);
        return ResponseEntity.ok(aluno);
    }

    /**
     * GET /alunos/matricula/{matricula}
     * Busca um aluno por matrícula
     */
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Aluno> buscarPorMatricula(@PathVariable String matricula) {
        Aluno aluno = alunoService.buscarPorMatricula(matricula);
        return ResponseEntity.ok(aluno);
    }

    /**
     * GET /alunos/plano/{plano}
     * Busca alunos por plano
     */
    @GetMapping("/plano/{plano}")
    public ResponseEntity<List<Aluno>> buscarPorPlano(@PathVariable String plano) {
        List<Aluno> alunos = alunoService.buscarPorPlano(plano);
        return ResponseEntity.ok(alunos);
    }

    /**
     * GET /alunos/status/{status}
     * Busca alunos por status (ativo/inativo)
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Aluno>> buscarPorStatus(@PathVariable Boolean status) {
        List<Aluno> alunos = alunoService.buscarPorStatus(status);
        return ResponseEntity.ok(alunos);
    }

    /**
     * GET /alunos/plano/{plano}/status/{status}
     * Busca alunos por plano e status
     */
    @GetMapping("/plano/{plano}/status/{status}")
    public ResponseEntity<List<Aluno>> buscarPorPlanoEStatus(@PathVariable String plano, @PathVariable Boolean status) {
        List<Aluno> alunos = alunoService.buscarPorPlanoEStatus(plano, status);
        return ResponseEntity.ok(alunos);
    }

    /**
     * GET /alunos/academia/{academiaId}
     * Busca alunos de uma academia específica
     */
    @GetMapping("/academia/{academiaId}")
    public ResponseEntity<List<Aluno>> buscarPorAcademia(@PathVariable Long academiaId) {
        List<Aluno> alunos = alunoService.buscarPorAcademia(academiaId);
        return ResponseEntity.ok(alunos);
    }

    /**
     * GET /alunos/academia/{academiaId}/ativos
     * Busca alunos ativos de uma academia específica
     */
    @GetMapping("/academia/{academiaId}/ativos")
    public ResponseEntity<List<Aluno>> buscarAtivosDeAcademia(@PathVariable Long academiaId) {
        List<Aluno> alunos = alunoService.buscarAlunosAtivosDeAcademia(academiaId);
        return ResponseEntity.ok(alunos);
    }

    /**
     * GET /alunos/sem-academia
     * Busca alunos sem academia vinculada
     */
    @GetMapping("/sem-academia")
    public ResponseEntity<List<Aluno>> buscarSemAcademia() {
        List<Aluno> alunos = alunoService.buscarSemAcademia();
        return ResponseEntity.ok(alunos);
    }

    /**
     * GET /alunos/periodo
     * Busca alunos por período de início (query parameters)
     */
    @GetMapping("/periodo")
    public ResponseEntity<List<Aluno>> buscarPorPeriodo(
            @RequestParam String dataInicio,
            @RequestParam String dataFim) {
        List<Aluno> alunos = alunoService.buscarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(alunos);
    }

    /**
     * GET /alunos/estatisticas
     * Retorna estatísticas sobre alunos
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Long>> obterEstatisticas() {
        Map<String, Long> estatisticas = alunoService.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }

    /**
     * POST /alunos
     * Cria um novo aluno
     */
    @PostMapping
    public ResponseEntity<Aluno> criar(@Valid @RequestBody Aluno aluno) {
        Aluno salvo = alunoService.salvar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * PUT /alunos/{id}
     * Atualiza um aluno existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @Valid @RequestBody Aluno alunoAtualizado) {
        Aluno aluno = alunoService.buscarPorId(id);
        alunoAtualizado.setId(id);
        Aluno salvo = alunoService.salvar(alunoAtualizado);
        return ResponseEntity.ok(salvo);
    }

    /**
     * PATCH /alunos/{id}/inativar
     * Inativa um aluno (altera status para false)
     */
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Aluno> inativar(@PathVariable Long id) {
        Aluno aluno = alunoService.inativar(id);
        return ResponseEntity.ok(aluno);
    }

    /**
     * PATCH /alunos/{id}/ativar
     * Ativa um aluno (altera status para true)
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Aluno> ativar(@PathVariable Long id) {
        Aluno aluno = alunoService.ativar(id);
        return ResponseEntity.ok(aluno);
    }

    /**
     * PATCH /alunos/{id}/vincular-academia/{academiaId}
     * Vincula um aluno a uma academia
     */
    @PatchMapping("/{id}/vincular-academia/{academiaId}")
    public ResponseEntity<Aluno> vincularAcademia(@PathVariable Long id, @PathVariable Long academiaId) {
        Aluno aluno = alunoService.vincularAcademia(id, academiaId);
        return ResponseEntity.ok(aluno);
    }

    /**
     * PATCH /alunos/{id}/desvincular-academia
     * Desvincula um aluno de sua academia
     */
    @PatchMapping("/{id}/desvincular-academia")
    public ResponseEntity<Aluno> desvincularAcademia(@PathVariable Long id) {
        Aluno aluno = alunoService.desvincularAcademia(id);
        return ResponseEntity.ok(aluno);
    }

    /**
     * DELETE /alunos/{id}
     * Deleta um aluno
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Boolean deletado = alunoService.excluir(id);
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

