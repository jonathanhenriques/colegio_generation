package com.jonathan.colegiogeneration.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

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

    @NotBlank(message = "nome não pode ser vazio!")
    @Size(max = 200, message = "nome não pode ter mais que 200 caracteres")
    private String nome;

    @NotNull
    @Min(value = 1, message = "Idade precisa ser maior ou igual a 1")
    @Max(value = 150, message = "Idade precisa ser menor ou igual a 150")
    private Long idade;

    @NotNull
    @Min(value = 0, message = "Nota precisa ser maior ou igual a 0")
    @Max(value = 10, message = "Nota precisa ser menor ou igual a 10")
    private Long notaPrimeiroSemestre;

    @NotNull
    @Min(value = 0, message = "Nota precisa ser maior ou igual a 0")
    @Max(value = 10, message = "Nota precisa ser menor ou igual a 10")
    private Long notaSegundoSemestre;

    @NotBlank(message = "nomeProfessor não pode ser vazio!")
    private String nomeProfessor;

    @NotNull
    @Min(value = 1, message = "numeroDaSala precisa ser maior ou igual a 1")
    @Max(value = 150, message = "numeroDaSala precisa ser menor ou igual a 150")
    private Long numeroDaSala;
}
