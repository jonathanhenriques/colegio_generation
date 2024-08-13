package com.jonathan.colegiogeneration.api.controller;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.service.AlunoService;
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

    @GetMapping(value = "/{idAluno}",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = "application/json;charset=UTF-8"
    )
    Aluno getAlunoById(@PathVariable Long idAluno){
        return alunoService.getAlunoById(idAluno);
    }

    @GetMapping(
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json;charset=UTF-8")
    ResponseEntity<Page<Aluno>> getAllAluno(@PageableDefault(size = 5) Pageable pageable){
        return alunoService.getAllAluno(pageable);
    }

    @PostMapping
    ResponseEntity<Aluno> postAluno(@RequestBody Aluno aluno){
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.postAluno(aluno));
    }

    @PutMapping("/{id}")
    public Aluno putAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        this.getAlunoById(id);
        aluno.setId(id);
        return alunoService.putAluno(aluno);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Aluno> patchAluno(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        var aluno = this.getAlunoById(id);
        Aluno alunoAtualizado = alunoService.patchAluno(id, updates);
        return ResponseEntity.ok(alunoAtualizado);
    }



    //    @Operation(summary = "Deleta uma categoria pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteteAluno(@RequestBody Aluno idAluno){
        alunoService.deleteAluno(idAluno);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
