package com.jonathan.colegiogeneration.domain.service;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.reponsedto.AlunoDTO;
import com.jonathan.colegiogeneration.domain.reponsedto.AlunoDTOResponseAll;
import com.jonathan.colegiogeneration.domain.repository.filter.AlunoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public interface AlunoService {


    Aluno getAlunoById(Long idAluno);

    ResponseEntity<Page<Aluno>> getAllAluno(Pageable pageable);

    Aluno postAluno(Aluno aluno);

    Aluno putAluno(Aluno aluno);

    Aluno patchAluno(Long id, Map<String, Object> updates);

    void deleteAluno(Aluno aluno);

    Page<AlunoDTOResponseAll> buscarAlunosFiltrados(AlunoFilter filterDTO, Pageable pageable);


}
