package com.jonathan.colegiogeneration.domain.repository.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
public class AlunoFilter {

    private Long id;

    private String nome;


    private Long idade;


    private Long notaPrimeiroSemestre;


    private Long notaSegundoSemestre;


    private String nomeProfessor;


    private Long numeroDaSala;
}
