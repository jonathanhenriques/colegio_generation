package com.jonathan.colegiogeneration.api.exception;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * classe standart que monta o obj de erro a ser exibido
 *
 * @author hsjon
 *
 */
@AllArgsConstructor
@Getter
@Hidden
public class ApiErrors {


    private LocalDateTime timestamp;
    private Integer status;
    @Getter
//    private List<String> errors;
    private String errors;
    private String path;



}
