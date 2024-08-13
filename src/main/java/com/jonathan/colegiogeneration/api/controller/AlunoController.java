package com.jonathan.colegiogeneration.api.controller;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/alunos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AlunoController.class);

    @Operation(description = "Busca Aluno por id")
    @GetMapping(value = "/{idAluno}",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = "application/json;charset=UTF-8"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um aluno pelo id"),
            @ApiResponse(responseCode = "400", description = "Não existe o aluno com o id informado")
    })
    Aluno getAlunoById(@PathVariable Long idAluno){
        return alunoService.getAlunoById(idAluno);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um aluno pelo id")
    })
    @Operation(description = "Busca todos os Alunos")
    @GetMapping(
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json;charset=UTF-8")
    ResponseEntity<Page<Aluno>> getAllAluno(@PageableDefault(size = 5) Pageable pageable){
        return alunoService.getAllAluno(pageable);
    }

    @Operation(description = "Cria um Aluno")
    @PostMapping
    ResponseEntity<Aluno> postAluno(@RequestBody Aluno aluno){
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.postAluno(aluno));
    }

    @Operation(description = "Atualiza um Aluno por id")
    @PutMapping("/{id}")
    public Aluno putAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        this.getAlunoById(id);
        aluno.setId(id);
        return alunoService.putAluno(aluno);
    }

    @Operation(description = "Atualiza  Aluno por parametros")
    @PatchMapping("/{id}")
    public ResponseEntity<Aluno> patchAluno(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        var aluno = this.getAlunoById(id);
        Aluno alunoAtualizado = alunoService.patchAluno(id, updates);
        return ResponseEntity.ok(alunoAtualizado);
    }


    @Operation(description = "Deleta um Aluno por id")
    //    @Operation(summary = "Deleta uma categoria pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteteAluno(@RequestBody Aluno idAluno){
        alunoService.deleteAluno(idAluno);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
