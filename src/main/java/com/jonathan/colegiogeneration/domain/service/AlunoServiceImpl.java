package com.jonathan.colegiogeneration.domain.service;

import com.jonathan.colegiogeneration.api.exception.AlunoNaoEncontradoException;
import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class AlunoServiceImpl implements AlunoService {


    @Autowired
    private AlunoRepository alunoRepository;


    @Override
    public Aluno getAlunoById(Long idAluno) {
        return alunoRepository.findById(idAluno).orElseThrow(() -> new AlunoNaoEncontradoException(idAluno));
    }

    @Override
    public ResponseEntity<Page<Aluno>> getAllAluno(Pageable pageable) {
        log.info("Obtendo todos os Alunos");

        // Chama o serviço para obter a página de CategoriaProvaDTO
        Page<Aluno> page = alunoRepository.findAll(pageable);

        // Retorna a resposta paginada dentro de um ResponseEntity
        return ResponseEntity.ok(page);
    }

    @Override
    public Aluno postAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @Override
    public Aluno putAluno(Aluno aluno) {
        alunoRepository.findById(aluno.getId()).orElseThrow(() -> new AlunoNaoEncontradoException(aluno));
        return alunoRepository.save(aluno);
    }

    @Override
    public Aluno patchAluno(Long id, Map<String, Object> updates) {
        // Verifica se o aluno existe
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado! | " + id));

        // Atualiza os campos específicos, ignorando o campo 'id'
        updates.forEach((key, value) -> {
            if ("id".equals(key)) {
                // Ignorar o campo 'id'
                return;
            }
            switch (key) {
                case "nome":
                    aluno.setNome((String) value);
                    break;
                case "idade":
                    aluno.setIdade(((Number) value).longValue());
                    break;
                case "notaPrimeiroSemestre":
                    aluno.setNotaPrimeiroSemestre(((Number) value).longValue());
                    break;
                case "notaSegundoSemestre":
                    aluno.setNotaSegundoSemestre(((Number) value).longValue());
                    break;
                case "nomeProfessor":
                    aluno.setNomeProfessor((String) value);
                    break;
                case "numeroDaSala":
                    aluno.setNumeroDaSala(((Number) value).longValue());
                    break;
                default:
                    throw new IllegalArgumentException("Campo não encontrado: " + key);
            }
        });

        // Salva as alterações no repositório
        return alunoRepository.save(aluno);
    }



    @Override
    public void deleteAluno(Aluno aluno) {
        alunoRepository.delete(aluno);
    }
}
