package br.edu.infnet.gabriel.gym_management.controller;

import br.edu.infnet.gabriel.gym_management.model.Aluno;
import br.edu.infnet.gabriel.gym_management.service.AlunoService;
import br.edu.infnet.gabriel.gym_management.exception.AlunoInvalidoException;
import br.edu.infnet.gabriel.gym_management.exception.AlunoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável pelos endpoints relacionados a Alunos.
 * Todos os endpoints utilizam o prefixo "/alunos".
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
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Aluno aluno = alunoService.buscarPorId(id);
            return ResponseEntity.ok(aluno);
        } catch (AlunoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * GET /alunos/cpf/{cpf}
     * Busca um aluno por CPF
     */
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
        try {
            Aluno aluno = alunoService.buscarPorCpf(cpf);
            return ResponseEntity.ok(aluno);
        } catch (AlunoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * GET /alunos/matricula/{matricula}
     * Busca um aluno por matrícula
     */
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<?> buscarPorMatricula(@PathVariable String matricula) {
        try {
            Aluno aluno = alunoService.buscarPorMatricula(matricula);
            return ResponseEntity.ok(aluno);
        } catch (AlunoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
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
     * POST /alunos
     * Cria um novo aluno
     */
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Aluno aluno) {
        try {
            Aluno salvo = alunoService.salvar(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (AlunoInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * PUT /alunos/{id}
     * Atualiza um aluno existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Aluno alunoAtualizado) {
        try {
            Aluno aluno = alunoService.buscarPorId(id);
            alunoAtualizado.setId(id);
            Aluno salvo = alunoService.salvar(alunoAtualizado);
            return ResponseEntity.ok(salvo);
        } catch (AlunoInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        } catch (AlunoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * PATCH /alunos/{id}/inativar
     * Inativa um aluno (altera status para false)
     */
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            Aluno aluno = alunoService.inativar(id);
            return ResponseEntity.ok(aluno);
        } catch (AlunoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * PATCH /alunos/{id}/ativar
     * Ativa um aluno (altera status para true)
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long id) {
        try {
            Aluno aluno = alunoService.ativar(id);
            return ResponseEntity.ok(aluno);
        } catch (AlunoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
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

