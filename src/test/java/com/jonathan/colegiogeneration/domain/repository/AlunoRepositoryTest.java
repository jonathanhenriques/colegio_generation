package com.jonathan.colegiogeneration.domain.repository;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.reponsedto.AlunoDTO;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.jonathan.colegiogeneration.commom.AlunoConstantes.*;
import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class AlunoRepositoryTest {


    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    EntityManager entityManager;


    @Test
    @DisplayName("Deveria retornar um Aluno pelo ID")
    void findById_IdExistente_ReturnesTrue() {

//        AlunoDTO dto = new AlunoDTO(null,NOME, IDADE, NOTA_PRIMEIRO_SEMESTRE, NOTA_SEGUNDO_SEMESTRE, NOME_PROFESSOR, NUMERO_DA_SALA);
        this.creatAluno(ALUNODTO);
        //O ID será outro pois já existem dados no banco
        Optional<Aluno> resultado = alunoRepository.findById(ID);

        assertThat(resultado.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Deveria falhar e não retornar um Aluno pelo ID")
    void findById_IdInexistente_ReturnsEmpty() {

        Optional<Aluno> resultado = alunoRepository.findById(5L);

        assertThat(resultado.isEmpty()).isTrue();
    }


    private Aluno creatAluno(AlunoDTO dto) {
        Aluno aluno = new Aluno(dto);

        this.entityManager.persist(aluno);
        return aluno;
    }

}

