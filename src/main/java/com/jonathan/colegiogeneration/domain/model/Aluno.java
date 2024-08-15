package com.jonathan.colegiogeneration.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Schema(description = "Representa um aluno", hidden = true)
@Entity
@Table(name = "TB_ALUNO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Aluno extends RepresentationModel<Aluno> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do aluno", example = "Simone Biles")
    @NotBlank(message = "nome não pode ser vazio!")
    @Size(max = 200, message = "nome não pode ter mais que 200 caracteres")
    private String nome;

    @Schema(description = "Idade do aluno", example = "27")
    @NotNull
    @Min(value = 1, message = "Idade precisa ser maior ou igual a 1")
    @Max(value = 150, message = "Idade precisa ser menor ou igual a 150")
    private Long idade;

    @Schema(description = "Nota do primeiro semestre", example = "8")
    @NotNull
    @Min(value = 0, message = "Nota precisa ser maior ou igual a 0")
    @Max(value = 10, message = "Nota precisa ser menor ou igual a 10")
    private Long notaPrimeiroSemestre;

    @Schema(description = "Nota do segundo semestre", example = "7")
    @NotNull
    @Min(value = 0, message = "Nota precisa ser maior ou igual a 0")
    @Max(value = 10, message = "Nota precisa ser menor ou igual a 10")
    private Long notaSegundoSemestre;

    @Schema(description = "Nome do professor", example = "Professor Silva")
    @NotBlank(message = "nomeProfessor não pode ser vazio!")
    private String nomeProfessor;

    @Schema(description = "Número da sala", example = "101")
    @NotNull
    @Min(value = 1, message = "numeroDaSala precisa ser maior ou igual a 1")
    @Max(value = 150, message = "numeroDaSala precisa ser menor ou igual a 150")
    private Long numeroDaSala;
}
