package com.jonathan.colegiogeneration.api.controller;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.reponsedto.AlunoDTO;
import com.jonathan.colegiogeneration.domain.reponsedto.AlunoDTOResponseAll;
import com.jonathan.colegiogeneration.domain.repository.filter.AlunoFilter;
import com.jonathan.colegiogeneration.domain.service.AlunoService;
import com.jonathan.colegiogeneration.infra.AlunoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/alunos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlunoController {

    private final AlunoService alunoService;


    @Operation(description = "Busca Aluno por id")
    @GetMapping(value = "/{idAluno}",
            produces = "application/json;charset=UTF-8")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<EntityModel<AlunoDTO>> getAlunoById(@PathVariable Long idAluno) {
        Aluno aluno = alunoService.getAlunoById(idAluno);
        AlunoDTO dto = mapToAlunoDTO(aluno);

        EntityModel<AlunoDTO> alunoResource = EntityModel.of(dto);

        List<Link> links = Arrays.asList(getSelfLink(dto), getPostAlunoLink(), getPutAlunoLink(dto),
                getDeleteAlunoLink(dto), getAllAlunosLink());

        links.forEach(alunoResource::add);

        log.info("Alunocontroller GetById chamado para id  {}", aluno.getId());
        return ResponseEntity.ok(alunoResource);
    }




    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @Operation(description = "Busca todos os Alunos")
    @GetMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<PagedModel<EntityModel<AlunoDTOResponseAll>>> getAllAluno(@Parameter(hidden = true) @PageableDefault(size = 5) Pageable pageable) {

       List<AlunoDTO> alunosPageDto = alunoService.getAllAluno(pageable).getBody().stream().map(a -> {
            AlunoDTO dto = mapToAlunoDTO(a);
            return dto;
        }).collect(Collectors.toList());

        Page<AlunoDTO> alunosPage = new PageImpl<>(alunosPageDto);


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

    @Operation(description = "Busca todos os Alunos")
    @GetMapping(value = "/filtro", produces = "application/json;charset=UTF-8")
    public Page<Aluno> pesquisar(AlunoFilter filtro, @PageableDefault(size = 5) Pageable pageable) {
//        Page<AtendenteED> page = atendenteRepository.findAll(ExameSpecifications.usandoFiltro(filtro), pageable);
        return alunoService.findAll(AlunoSpecification.usandoFiltro(filtro), pageable);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @Operation(description = "Cria um Aluno")
    @PostMapping
    public ResponseEntity<EntityModel<AlunoDTO>> postAluno(@RequestBody AlunoDTO aluno) {
        AlunoDTO alunoSalvo = mapToAlunoDTO(alunoService.postAluno(mapToAluno(aluno)));

        EntityModel<AlunoDTO> alunoResource = EntityModel.of(alunoSalvo);

        List<Link> links = Arrays.asList(getSelfLink(alunoSalvo), getByIdAlunoLink(alunoSalvo.getId()), getPutAlunoLink(alunoSalvo),
                getDeleteAlunoLink(alunoSalvo), getAllAlunosLink());

        links.forEach(alunoResource::add);

        log.info("Alunocontroller postAluno chamado e criado / {}", alunoSalvo.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoResource);
    }



    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @Operation(description = "Atualiza um Aluno por id")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AlunoDTO>> putAluno(
            @PathVariable Long id,
            @RequestBody @Schema(description = "Detalhes do aluno", example = """
        {
            "nome": "Simone Biles",
            "idade": 27,
            "notaPrimeiroSemestre": 8,
            "notaSegundoSemestre": 7,
            "nomeProfessor": "Professor Silva",
            "numeroDaSala": 101
        }""") AlunoDTO aluno) {
        aluno.setId(id);
        AlunoDTO alunoAtualizado = mapToAlunoDTO(alunoService.putAluno(mapToAluno(aluno)));
        EntityModel<AlunoDTO> alunoResource = EntityModel.of(alunoAtualizado);

        List<Link> links = Arrays.asList(
                getSelfLink(alunoAtualizado),
                getByIdAlunoLink(alunoAtualizado.getId()),
                getPostAlunoLink(),
                getDeleteAlunoLink(alunoAtualizado),
                getAllAlunosLink()
        );

        links.forEach(alunoResource::add);

        log.info("Alunocontroller putAluno chamado e atualizado / {}", alunoAtualizado.getId());

        return ResponseEntity.ok(alunoResource);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @Operation(description = "Atualiza um Aluno por parâmetros")
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<AlunoDTO>> patchAluno(@PathVariable Long id, @RequestBody @Schema(description = "Detalhes do aluno", example = """
        {
            "nome": "Simone Biles",
            "idade": 27,
            "notaPrimeiroSemestre": 8,
            "notaSegundoSemestre": 7,
            "nomeProfessor": "Professor Silva",
            "numeroDaSala": 101
        }""")  Map<String, Object> updates) {
        // Atualiza o aluno
        log.info("patch id passado / {}", id);
        AlunoDTO alunoAtualizado = mapToAlunoDTO(alunoService.patchAluno(id, updates));

        EntityModel<AlunoDTO> alunoResource = EntityModel.of(alunoAtualizado);

        List<Link> links = Arrays.asList(getSelfLink(alunoAtualizado), getByIdAlunoLink(alunoAtualizado.getId()), getPostAlunoLink(),
                getPutAlunoLink(alunoAtualizado),getDeleteAlunoLink(alunoAtualizado), getAllAlunosLink());

        links.forEach(alunoResource::add);

        log.info("Alunocontroller pathAluno chamado e atualiado / {}", alunoAtualizado.getId());

        return ResponseEntity.ok(alunoResource);
    }



    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @Operation(description = "Deleta um Aluno por id")
    @DeleteMapping("/{idAluno}")
    public ResponseEntity<?> deleteAluno(@PathVariable Long idAluno) {
        var aluno = getAlunoById(idAluno).getBody().getContent();
        Aluno alunoConvertido = mapToAluno(aluno);
        alunoService.deleteAluno(alunoConvertido);

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

    private static Aluno mapToAluno(AlunoDTO aluno) {
        Aluno alunoConvertido = Aluno.builder()
                .id(aluno.getId()).nome(aluno.getNome()).idade(aluno.getIdade())
                .notaPrimeiroSemestre(aluno.getNotaPrimeiroSemestre()).notaSegundoSemestre(aluno.getNotaSegundoSemestre())
                .nomeProfessor(aluno.getNomeProfessor()).numeroDaSala(aluno.getNumeroDaSala()).build();
        return alunoConvertido;
    }


    private static Link getSelfLink(AlunoDTO aluno) {
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

    private static Link getDeleteAlunoLink(AlunoDTO aluno) {
        return linkTo(AlunoController.class).slash("alunos").slash(aluno.getId()).withRel("deletar-aluno");
    }

    private static Link getPutAlunoLink(AlunoDTO aluno) {
        return linkTo(AlunoController.class).slash("alunos").slash(aluno.getId()).withRel("atualizar-aluno");
    }

    private AlunoDTO mapToAlunoDTO(Aluno aluno) {
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(aluno.getId());
        alunoDTO.setNome(aluno.getNome());
        alunoDTO.setIdade(aluno.getIdade());
        alunoDTO.setNotaPrimeiroSemestre(aluno.getNotaPrimeiroSemestre());

        alunoDTO.setNotaSegundoSemestre(aluno.getNotaSegundoSemestre());
        alunoDTO.setNomeProfessor(aluno.getNomeProfessor());
        alunoDTO.setNumeroDaSala(aluno.getNumeroDaSala());
        return alunoDTO;
    }




}
