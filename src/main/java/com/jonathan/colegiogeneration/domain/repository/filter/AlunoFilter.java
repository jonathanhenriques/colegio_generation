package com.jonathan.colegiogeneration.domain.repository.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlunoFilter {

    private Long id;

    private String nome;


    private Long idade;


    private Long notaPrimeiroSemestre;


    private Long notaSegundoSemestre;


    private String nomeProfessor;


    private Long numeroDaSala;
}
