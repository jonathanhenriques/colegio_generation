package com.jonathan.colegiogeneration.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Builder
@Getter
@Schema(name = "Problema")
public class Problem {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "https://colegiogeneration.com.br/dados-invalidos")
    private String type;

    @Schema(example = "Dados inválidos")
    private String title;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    @Schema(example = "2024-08-15T11:21:50.902245498Z")
    private OffsetDateTime timestamp;

    @Schema(description = "Lista de objetos ou campos que geraram o erro")
    private List<Object> objects;

    //Qualuer propriedade ou classe que estiver com excessoes
    @Builder
    @Getter
    @Schema(name = "ObjetoProblema")
    public static class Object {

        @Schema(example = "nome")
        private String name;

        @Schema(example = "O nome é inválido")
        private String userMessage;

    }

}
