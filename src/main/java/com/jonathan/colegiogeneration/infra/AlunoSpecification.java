package com.jonathan.colegiogeneration.infra;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.repository.filter.AlunoFilter;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;

public class AlunoSpecification {

    public static Specification<Aluno> usandoFiltro(AlunoFilter filtro){
        return (root, query, builder) -> {

//            if (AtendenteED.class.equals(query.getResultType())) {
//                root.fetch("paciente");
//                root.fetch("medico");
//                root.fetch("atendente");
//                root.fetch("local");
//            }

            var predicates = new ArrayList<Predicate>();

            if(filtro.getId() != null)
                predicates.add(builder.equal(root.get("id"), filtro.getId()));

            if(filtro.getNome() != null)
                predicates.add(builder.equal(root.get("nome"), filtro.getNome()));

//            if(filtro.getIsAtivo() != null)
//                predicates.add(builder.equal(root.get("isAtivo"), filtro.getIsAtivo()));

            //para transformar uma Collection em um array
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
