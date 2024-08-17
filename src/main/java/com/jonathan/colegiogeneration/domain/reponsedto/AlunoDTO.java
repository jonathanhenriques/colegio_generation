package com.jonathan.colegiogeneration.domain.reponsedto;


import com.jonathan.colegiogeneration.domain.model.Aluno;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa um alunoDTO", hidden = true)
public class AlunoDTO {

    private Long id;

    private String nome;

    private Long idade;
    private Long notaPrimeiroSemestre;

    private Long notaSegundoSemestre;
    private String nomeProfessor;

    private Long numeroDaSala;

    public AlunoDTO (Aluno aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.idade = aluno.getIdade();
        this.notaPrimeiroSemestre = aluno.getNotaPrimeiroSemestre();
        this.notaSegundoSemestre = aluno.getNotaSegundoSemestre();
        this.nomeProfessor = aluno.getNomeProfessor();
        this.numeroDaSala = aluno.getNumeroDaSala();
    }
}