package com.jonathan.colegiogeneration.domain.service;


import com.jonathan.colegiogeneration.api.exception.AlunoNaoEncontradoException;
import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.repository.AlunoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.jonathan.colegiogeneration.commom.AlunoConstantes.ALUNO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional // Garante que cada teste seja executado dentro de uma transação
class AlunoServiceImplTest {

    @MockBean
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoService alunoService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @AfterEach
    void tearDown() {

    }




//    VERSAO DOS TESTES USANDO REPOSITORY REAL INDO AO BANCO HQ

//    @Autowired
//    private AlunoRepository alunoRepository;

//    @Autowired
//    private EntityManager entityManager;


//    @BeforeEach
//    void setUp() {
//
//        entityManager.merge(ALUNO); // Reattaches if detached
//    }

//    @AfterEach
//    void tearDown() {
//        // Limpa o banco após cada teste
//        alunoRepository.deleteAll();
//        entityManager.flush();
//    }


//    @Test
//    @DisplayName("Deveria retornar um Aluno pelo ID existente")
//    void getAlunoById_IdExistente_ReturnsAluno() {
//
//
//        Aluno aluno = alunoService.getAlunoById(ALUNO.getId());
//
//        assertNotNull(aluno, "O aluno encontrado não deve ser null");
//        assertEquals(ALUNO.getId(), aluno.getId(), "O aluno encontrado deve ter o ID esperado");
//        assertEquals(aluno, ALUNO, "Verifica se o aluno buscado é igual ao aluno salvo no banco");
//    }
//
//    @Test
//    @DisplayName("Deveria lançar uma exceção AlunoNaoEncontradoException quando o ID não existir")
//    void getAlunoById_IdInexistente_ThrowsAlunoNaoEncontradoException() {
//        assertThrows(AlunoNaoEncontradoException.class, () -> alunoService.getAlunoById(100L));
//    }



    @Test
    @DisplayName("Deveria retornar um Aluno pelo ID existente")
    void getAlunoById_IdExistente_ReturnsAluno() {
        // Configuração do mock para o ID existente
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(ALUNO));

        // Execução do método
        Aluno aluno = alunoService.getAlunoById(1L);

        // Verificações
        assertNotNull(aluno, "O aluno encontrado não deve ser null");
        assertEquals(ALUNO.getId(), aluno.getId(), "O aluno encontrado deve ter o ID esperado");
        assertEquals(ALUNO, aluno, "Verifica se o aluno buscado é igual ao aluno salvo no banco");
    }

    @Test
    @DisplayName("Deveria lançar uma exceção AlunoNaoEncontradoException quando o ID não existir")
    void getAlunoById_IdInexistente_ThrowsAlunoNaoEncontradoException() {
        // Configuração do mock para o ID inexistente
        when(alunoRepository.findById(100L)).thenReturn(Optional.empty());

        // Execução do método e verificação da exceção
        assertThrows(AlunoNaoEncontradoException.class, () -> alunoService.getAlunoById(100L));
    }

    @Test
    @DisplayName("Deveria retornar todos os alunos com paginação")
    void getAllAluno_ReturnsPageOfAlunos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Aluno> page = new PageImpl<>(List.of(ALUNO));
        when(alunoRepository.findAll(pageable)).thenReturn(page);

        ResponseEntity<Page<Aluno>> response = alunoService.getAllAluno(pageable);

        assertNotNull(response);
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(ALUNO, response.getBody().getContent().get(0));
    }

    @Test
    @DisplayName("Deveria criar um novo Aluno")
    void postAluno_CreatesAndReturnsAluno() {
        // Supondo que ALUNO não tem ID definido inicialmente
        Aluno alunoEsperado = new Aluno();
        alunoEsperado.setNome("João da Silva");
        alunoEsperado.setId(1L);  // ID gerado pelo mock, se aplicável

        // Configura o mock para retornar um aluno com ID
        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoEsperado);

        // Chama o método de serviço
        Aluno alunoRetornado = alunoService.postAluno(new Aluno());

        // Verifica se o aluno retornado não é nulo
        assertNotNull(alunoRetornado);
        // Verifica se o ID do aluno retornado não é nulo
        assertNotNull(alunoRetornado.getId());
        // Verifica se o nome do aluno retornado é o mesmo do objeto esperado
        assertEquals(alunoEsperado.getNome(), alunoRetornado.getNome());
        // Verifica se o ID do aluno retornado é o mesmo do objeto esperado
        assertEquals(alunoEsperado.getId(), alunoRetornado.getId());


//        // Captura o argumento passado para o método save
//        ArgumentCaptor<Aluno> captor = ArgumentCaptor.forClass(Aluno.class);
//        verify(alunoRepository).save(captor.capture());
//        Aluno alunoCapturado = captor.getValue();
//
//        // Verifica que o aluno capturado tem o nome esperado
//        assertEquals(ALUNO.getNome(), alunoCapturado.getNome());


    }


    @Test
    @DisplayName("Deveria atualizar um Aluno existente")
    void putAluno_UpdatesAndReturnsAluno() {
        when(alunoRepository.findById(ALUNO.getId())).thenReturn(Optional.of(ALUNO));
        when(alunoRepository.save(ALUNO)).thenReturn(ALUNO);

        Aluno aluno = alunoService.putAluno(ALUNO);

        assertNotNull(aluno);
        assertEquals(ALUNO.getId(), aluno.getId());
    }

    @Test
    @DisplayName("Deveria lançar AlunoNaoEncontradoException ao tentar atualizar um Aluno inexistente")
    void putAluno_IdInexistente_ThrowsAlunoNaoEncontradoException() {
        when(alunoRepository.findById(ALUNO.getId())).thenReturn(Optional.empty());

        assertThrows(AlunoNaoEncontradoException.class, () -> alunoService.putAluno(ALUNO));
    }

    @Test
    @DisplayName("Deveria atualizar campos específicos de um Aluno (patch)")
    void patchAluno_UpdatesSpecificFieldsAndReturnsAluno() {
        when(alunoRepository.findById(ALUNO.getId())).thenReturn(Optional.of(ALUNO));
        when(alunoRepository.save(ALUNO)).thenReturn(ALUNO);

        Map<String, Object> updates = Map.of("nome", "Novo Nome", "idade", 25);
        Aluno aluno = alunoService.patchAluno(ALUNO.getId(), updates);

        assertNotNull(aluno);
        assertEquals("Novo Nome", aluno.getNome());
        assertEquals(25, aluno.getIdade());
    }

    @Test
    @DisplayName("Deveria lançar AlunoNaoEncontradoException ao tentar aplicar patch em um Aluno inexistente")
    void patchAluno_IdInexistente_ThrowsAlunoNaoEncontradoException() {
        when(alunoRepository.findById(ALUNO.getId())).thenReturn(Optional.empty());

        assertThrows(AlunoNaoEncontradoException.class, () -> alunoService.patchAluno(ALUNO.getId(), Map.of("nome", "Novo Nome")));
    }

    @Test
    @DisplayName("Deveria deletar um Aluno existente")
    void deleteAluno_DeletesAluno() {
        // Configurando o mock para simular que o aluno existe
        when(alunoRepository.existsById(ALUNO.getId())).thenReturn(true);
        when(alunoRepository.findById(ALUNO.getId())).thenReturn(Optional.of(ALUNO));
        doNothing().when(alunoRepository).delete(ALUNO);

        // Chamando o método de serviço
        alunoService.deleteAluno(ALUNO);

        // Verificando se o método delete foi chamado exatamente uma vez
        verify(alunoRepository, times(1)).delete(ALUNO);
    }


    @Test
    @DisplayName("Deveria lançar AlunoNaoEncontradoException ao tentar deletar um Aluno inexistente")
    void deleteAluno_IdInexistente_ThrowsAlunoNaoEncontradoException() {
        when(alunoRepository.findById(ALUNO.getId())).thenReturn(Optional.empty());

        assertThrows(AlunoNaoEncontradoException.class, () -> alunoService.deleteAluno(ALUNO));
    }
}