package com.jonathan.colegiogeneration.api.controller;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.web.ErrorResponse;
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
            @ApiResponse(responseCode = "200", description = "Retorna uma lista paginada de alunos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Busca todos os Alunos")
    @GetMapping(produces = "application/json;charset=UTF-8")
    ResponseEntity<Page<Aluno>> getAllAluno(@PageableDefault(size = 5) Pageable pageable){
        return alunoService.getAllAluno(pageable);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Cria um Aluno")
    @PostMapping
    ResponseEntity<Aluno> postAluno(@RequestBody Aluno aluno){
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.postAluno(aluno));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Atualiza um Aluno por id")
    @PutMapping("/{id}")
    public Aluno putAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        this.getAlunoById(id);
        aluno.setId(id);
        return alunoService.putAluno(aluno);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Atualiza um Aluno por parâmetros")
    @PatchMapping("/{id}")
    public ResponseEntity<Aluno> patchAluno(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        var aluno = this.getAlunoById(id);
        Aluno alunoAtualizado = alunoService.patchAluno(id, updates);
        return ResponseEntity.ok(alunoAtualizado);
    }



    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Deleta um Aluno por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAluno(@PathVariable Long idAluno) {
        var aluno = getAlunoById(idAluno);
        alunoService.deleteAluno(aluno);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
