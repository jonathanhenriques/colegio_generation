package com.jonathan.colegiogeneration.domain.reponsedto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa um alunodto")
public class AlunoDTO {

        private Long id;

        private String nome;

        private Long idade;
        private Long notaPrimeiroSemestre;

        private Long notaSegundoSemestre;
        private String nomeProfessor;

        private Long numeroDaSala;
}