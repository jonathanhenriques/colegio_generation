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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            produces = "application/json;charset=UTF-8")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um aluno pelo id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Não existe o aluno com o id informado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<Aluno>> getAlunoById(@PathVariable Long idAluno) {
        Aluno aluno = alunoService.getAlunoById(idAluno);

        // Cria o EntityModel com o aluno
        EntityModel<Aluno> alunoResource = EntityModel.of(aluno);

        // Adiciona links HATEOAS
        WebMvcLinkBuilder selfLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAlunoById(idAluno));
        alunoResource.add(selfLinkBuilder.withSelfRel());

        WebMvcLinkBuilder allAlunosLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAllAluno(Pageable.unpaged()));
        alunoResource.add(allAlunosLinkBuilder.withRel("obter-todos-alunos"));

        return ResponseEntity.ok(alunoResource);
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
    public ResponseEntity<PagedModel<EntityModel<Aluno>>> getAllAluno(@PageableDefault(size = 5) Pageable pageable) {
        Page<Aluno> alunosPage = alunoService.getAllAluno(pageable).getBody();

        // Transforma cada aluno em um EntityModel com links HATEOAS
        List<EntityModel<Aluno>> alunoResources = alunosPage.stream()
                .map(aluno -> {
                    EntityModel<Aluno> alunoResource = EntityModel.of(aluno);
                    alunoResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAlunoById(aluno.getId())).withSelfRel());
                    return alunoResource;
                })
                .toList();

        // Constrói o PagedModel para suportar paginação
        PagedModel<EntityModel<Aluno>> pagedModel = PagedModel.of(alunoResources,
                new PagedModel.PageMetadata(alunosPage.getSize(),
                        alunosPage.getNumber(),
                        alunosPage.getTotalElements()));

        // Adiciona link para a própria coleção paginada
        pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAllAluno(pageable)).withSelfRel());

        // Adiciona link para criar um novo aluno
        pagedModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).postAluno(null)).withRel("criar-aluno"));

        return ResponseEntity.ok(pagedModel);
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
    public ResponseEntity<EntityModel<Aluno>> postAluno(@RequestBody Aluno aluno) {
        Aluno alunoCriado = alunoService.postAluno(aluno);

        // Cria o EntityModel com o aluno criado
        EntityModel<Aluno> alunoResource = EntityModel.of(alunoCriado);

        // Adiciona link para o próprio recurso (o aluno criado)
        WebMvcLinkBuilder selfLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAlunoById(alunoCriado.getId()));
        alunoResource.add(selfLinkBuilder.withSelfRel());

        // Adiciona link para a lista de todos os alunos
        WebMvcLinkBuilder allAlunosLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAllAluno(Pageable.unpaged()));
        alunoResource.add(allAlunosLinkBuilder.withRel("obter-todos-alunos"));

        return ResponseEntity.created(selfLinkBuilder.toUri()).body(alunoResource);
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
    public ResponseEntity<EntityModel<Aluno>> putAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        // Atualiza o aluno
        aluno.setId(id);
        Aluno alunoAtualizado = alunoService.putAluno(aluno);

        // Cria o EntityModel com o aluno atualizado
        EntityModel<Aluno> alunoResource = EntityModel.of(alunoAtualizado);

        // Adiciona link para o próprio recurso (o aluno atualizado)
        WebMvcLinkBuilder selfLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAlunoById(id));
        alunoResource.add(selfLinkBuilder.withSelfRel());

        // Adiciona link para a lista de todos os alunos
        WebMvcLinkBuilder allAlunosLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAllAluno(Pageable.unpaged()));
        alunoResource.add(allAlunosLinkBuilder.withRel("obter-todos-alunos"));

        return ResponseEntity.ok(alunoResource);
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
    public ResponseEntity<EntityModel<Aluno>> patchAluno(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        // Atualiza o aluno
        Aluno alunoAtualizado = alunoService.patchAluno(id, updates);

        // Cria o EntityModel com o aluno atualizado
        EntityModel<Aluno> alunoResource = EntityModel.of(alunoAtualizado);

        // Adiciona link para o próprio recurso (o aluno atualizado)
        WebMvcLinkBuilder selfLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAlunoById(id));
        alunoResource.add(selfLinkBuilder.withSelfRel());

        // Adiciona link para a lista de todos os alunos
        WebMvcLinkBuilder allAlunosLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAllAluno(Pageable.unpaged()));
        alunoResource.add(allAlunosLinkBuilder.withRel("obter-todos-alunos"));

        return ResponseEntity.ok(alunoResource);
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
    @DeleteMapping("/{idAluno}")
    public ResponseEntity<?> deleteAluno(@PathVariable Long idAluno) {
        var aluno = getAlunoById(idAluno).getBody().getContent();
        alunoService.deleteAluno(aluno);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
