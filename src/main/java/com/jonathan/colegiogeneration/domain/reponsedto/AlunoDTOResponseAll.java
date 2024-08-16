package com.jonathan.colegiogeneration.domain.reponsedto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Schema(description = "Representa um aluno", hidden = true)
@Builder
public record AlunoDTOResponseAll(

        Long id,
        @NotBlank(message = "nome não pode ser vazio!")
        @Size(max = 200, message = "nome não pode ter mais que 200 caracteres")
        String nome
) {
}
