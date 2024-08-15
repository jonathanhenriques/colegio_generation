package com.jonathan.colegiogeneration.domain.service;

import com.jonathan.colegiogeneration.api.controller.AlunoController;
import com.jonathan.colegiogeneration.api.exception.AlunoNaoEncontradoException;
import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.repository.AlunoRepository;
import com.jonathan.colegiogeneration.domain.repository.filter.AlunoFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RequiredArgsConstructor
@Service
public class AlunoServiceImpl implements AlunoService {


    private final AlunoRepository alunoRepository;


    @Override
    public Aluno getAlunoById(Long idAluno) {
        log.info("AlunoService GetById chamado para id  + " + idAluno);

        return alunoRepository.findById(idAluno).orElseThrow(() -> new AlunoNaoEncontradoException(idAluno));
    }

    @Override
    public ResponseEntity<Page<Aluno>> getAllAluno(Pageable pageable) {
        log.info("Obtendo todos os Alunos");

        // Chama o serviço para obter a página de CategoriaProvaDTO
        Page<Aluno> page = alunoRepository.findAll(pageable);

        // Retorna a resposta paginada dentro de um ResponseEntity

        log.info("AlunoService getAll chamado para + " + page.getTotalElements());
        return ResponseEntity.ok(page);
    }

    @Override
    public Aluno postAluno(Aluno aluno) {
        log.info("AlunoService postAluno chamado " );
        return alunoRepository.save(aluno);
    }

    @Override
    public Aluno putAluno(Aluno aluno) {
        alunoRepository.findById(aluno.getId()).orElseThrow(() -> new AlunoNaoEncontradoException(aluno));
        log.info("AlunoService putAluno chamado para id " + aluno.getId());
        return alunoRepository.save(aluno);
    }

    public Aluno patchAluno(Long id, Map<String, Object> updates) {
        log.info("AlunoService patchAluno chamado ");

        // Verifica se o aluno existe
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado! | " + id));

        // Atualiza os campos específicos
        updates.forEach((key, value) -> {
            if (!"id".equals(key)) { // Ignorar o campo 'id'
                try {
                    // Converte o valor para o tipo correto
                    Object convertedValue = convertValue(key, value, aluno);
                    // Atualiza o campo usando PropertyDescriptor
                    setProperty(aluno, key, convertedValue);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Erro ao atualizar o campo: " + key, e);
                }
            }
        });

        log.info("AlunoService patchAluno executado para id " + aluno.getId());

        // Salva as alterações no repositório
        return alunoRepository.save(aluno);
    }

    private Object convertValue(String key, Object value, Aluno aluno) {
        try {
            // Obtém o tipo do campo a partir do objeto aluno
            Field field = aluno.getClass().getDeclaredField(key);
            Class<?> fieldType = field.getType();

            // Converte o valor para o tipo apropriado com base no tipo do campo
            if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                return ((Number) value).longValue();
            } else if (fieldType.equals(String.class)) {
                return value.toString();
            } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                return ((Number) value).intValue();
            }
            // Adicione mais tipos conforme necessário

            return value; // Retorna o valor original se o tipo não for mapeado

        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Campo não encontrado: " + key, e);
        }
    }

    private void setProperty(Object obj, String propertyName, Object value) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
        Method writeMethod = pd.getWriteMethod();
        if (writeMethod != null) {
            writeMethod.invoke(obj, value);
        } else {
            throw new IllegalArgumentException("Não é possível definir a propriedade: " + propertyName);
        }
    }



    @Override
    public void deleteAluno(Aluno aluno) {
        log.info("AlunoService deleteAluno chamado para id " + aluno.getId());
        alunoRepository.delete(aluno);
    }

    @Override
    public Page<Aluno> findAll(AlunoFilter filtro, Pageable pageable) {
        // Chama o método findByFilters passando os parâmetros do filtro
        return alunoRepository.findByFilters(
                filtro.getId(),
                filtro.getNome(),
                filtro.getIdade(),
                filtro.getNotaPrimeiroSemestre(),
                filtro.getNotaSegundoSemestre(),
                filtro.getNomeProfessor(),
                filtro.getNumeroDaSala(),
                pageable
        );
    }
}
