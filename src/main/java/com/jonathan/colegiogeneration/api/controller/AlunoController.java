package com.jonathan.colegiogeneration.api.controller;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.reponsedto.AlunoDTOResponseAll;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/alunos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoController {

    private final AlunoService alunoService;

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
                            schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EntityModel<Aluno>> getAlunoById(@PathVariable Long idAluno) {
        Aluno aluno = alunoService.getAlunoById(idAluno);

        EntityModel<Aluno> alunoResource = EntityModel.of(aluno);

        List<Link> links = Arrays.asList(getSelfLink(aluno), getPostAlunoLink(), getPutAlunoLink(aluno),
                getDeleteAlunoLink(aluno), getAllAlunosLink());

        links.forEach(alunoResource::add);




        log.info("Alunocontroller GetById chamado para id  {}", aluno.getId());
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
    public ResponseEntity<PagedModel<EntityModel<AlunoDTOResponseAll>>> getAllAluno(@PageableDefault(size = 5) Pageable pageable) {
        Page<Aluno> alunosPage = alunoService.getAllAluno(pageable).getBody();

        // Transforma cada aluno em um EntityModel com links HATEOAS
        List<EntityModel<AlunoDTOResponseAll>> alunoResources = alunosPage.stream()
                .map(aluno -> {
                    AlunoDTOResponseAll dto = AlunoDTOResponseAll.builder().id(aluno.getId()).nome(aluno.getNome()).build();
                    EntityModel<AlunoDTOResponseAll> alunoResource = EntityModel.of(dto);

                    alunoResource.add(getSelfLink(aluno));

                    return alunoResource;
                })
                .toList();

        // Constrói o PagedModel para suportar paginação
        PagedModel<EntityModel<AlunoDTOResponseAll>> pagedModel = PagedModel.of(alunoResources,
                new PagedModel.PageMetadata(alunosPage.getSize(),
                        alunosPage.getNumber(),
                        alunosPage.getTotalElements()));

        pagedModel.add(linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).getAllAluno(pageable)).withSelfRel());

        pagedModel.add(linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class).postAluno(null)).withRel("criar-aluno"));

        log.info("Alunocontroller GetAll chamado com / {} Alunos", alunosPage.getTotalElements());

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
        Aluno alunoSalvo = alunoService.postAluno(aluno);

        EntityModel<Aluno> alunoResource = EntityModel.of(aluno);

        List<Link> links = Arrays.asList(getSelfLink(alunoSalvo), getByIdAlunoLink(alunoSalvo.getId()), getPutAlunoLink(alunoSalvo),
                getDeleteAlunoLink(alunoSalvo), getAllAlunosLink());

        links.forEach(alunoResource::add);

        log.info("Alunocontroller postAluno chamado e criado / {}", alunoSalvo.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoResource);
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
        EntityModel<Aluno> alunoResource = EntityModel.of(aluno);

        List<Link> links = Arrays.asList(getSelfLink(alunoAtualizado), getByIdAlunoLink(alunoAtualizado.getId()), getPostAlunoLink(),
                getDeleteAlunoLink(alunoAtualizado), getAllAlunosLink());

        links.forEach(alunoResource::add);

        log.info("Alunocontroller putAluno chamado e atualiado / {}", alunoAtualizado.getId());

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
        log.info("patch id passado / {}", id);
        Aluno alunoAtualizado = alunoService.patchAluno(id, updates);

        EntityModel<Aluno> alunoResource = EntityModel.of(alunoAtualizado);

        List<Link> links = Arrays.asList(getSelfLink(alunoAtualizado), getByIdAlunoLink(alunoAtualizado.getId()), getPostAlunoLink(),
                getPutAlunoLink(alunoAtualizado),getDeleteAlunoLink(alunoAtualizado), getAllAlunosLink());

        links.forEach(alunoResource::add);

        log.info("Alunocontroller pathAluno chamado e atualiado / {}", alunoAtualizado.getId());

        return ResponseEntity.ok(alunoResource);
    }



    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno excluído com sucesso"),
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

        // Cria um Map para a mensagem
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("mensagem", "Aluno excluído com sucesso");

        // Cria o EntityModel com o Map
        EntityModel<Map<String, String>> responseResource = EntityModel.of(responseMessage);

        List<Link> links = Arrays.asList(getPostAlunoLink(), getAllAlunosLink());

        links.forEach(responseResource::add);

        log.info("Alunocontroller deleteAluno chamado e deletado ");

        // Retorna a resposta com a mensagem de exclusão e os links HATEOAS
        return ResponseEntity.ok(responseResource);
    }



    private static Link getSelfLink(Aluno aluno) {
        return linkTo(AlunoController.class).slash(aluno.getId()).withSelfRel();
    }

    private static Link getByIdAlunoLink(Long id) {
        return linkTo(AlunoController.class).slash("alunos").slash(id).withRel("obter-por-id-aluno");
    }


    private static Link getPostAlunoLink() {
        return linkTo(AlunoController.class).slash("alunos").withRel("criar-aluno");
    }

    private static Link getAllAlunosLink() {
        return linkTo(AlunoController.class).slash("alunos").withRel("obter-todos-alunos");
    }

    private static Link getDeleteAlunoLink(Aluno aluno) {
        return linkTo(AlunoController.class).slash("alunos").slash(aluno.getId()).withRel("deletar-aluno");
    }

    private static Link getPutAlunoLink(Aluno aluno) {
        return linkTo(AlunoController.class).slash("alunos").slash(aluno.getId()).withRel("atualizar-aluno");
    }





}
