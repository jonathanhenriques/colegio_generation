package com.jonathan.colegiogeneration.domain.repository;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT a FROM Aluno a WHERE " +
            "(:id IS NULL OR a.id = :id) AND " +
            "(:nome IS NULL OR a.nome LIKE %:nome%) AND "  +
            "(:idade IS NULL OR a.idade = :idade) AND " +
            "(:notaPrimeiroSemestre IS NULL OR a.notaPrimeiroSemestre = :notaPrimeiroSemestre) AND " +
            "(:notaSegundoSemestre IS NULL OR a.notaSegundoSemestre = :notaSegundoSemestre) AND " +
            "(:nomeProfessor IS NULL OR a.nomeProfessor = :nomeProfessor) AND " +
            "(:numeroDaSala IS NULL OR a.numeroDaSala = :numeroDaSala)")
    Page<Aluno> findByFilters(@Param("id") Long id,
                              @Param("nome") String nome,
                              @Param("idade") Long idade,
                              @Param("notaPrimeiroSemestre") Long notaPrimeiroSemestre,
                              @Param("notaSegundoSemestre") Long notaSegundoSemestre,
                              @Param("nomeProfessor") String nomeProfessor,
                              @Param("numeroDaSala") Long numeroDaSala,
                              Pageable pageable);
}
