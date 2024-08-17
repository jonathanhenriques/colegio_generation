package com.jonathan.colegiogeneration.commom;

import com.jonathan.colegiogeneration.domain.model.Aluno;
import com.jonathan.colegiogeneration.domain.reponsedto.AlunoDTO;


public class AlunoConstantes {

        public static final Long ID = 1L;
        public static final String NOME = "test nome";
        public static final Long IDADE = 17L;
        public static final Long NOTA_PRIMEIRO_SEMESTRE = 9L;
        public static final Long NOTA_SEGUNDO_SEMESTRE = 8L;
        public static final String NOME_PROFESSOR = "test Prof";
        public static final Long NUMERO_DA_SALA = 1L;

        public static final Aluno ALUNO = new Aluno(1L, NOME, IDADE, NOTA_PRIMEIRO_SEMESTRE, NOTA_SEGUNDO_SEMESTRE, NOME_PROFESSOR, NUMERO_DA_SALA);
//        public static final Aluno ALUNO_ATIVO = new Aluno(1l, NOME, IDADE, NOTA_PRIMEIRO_SEMESTRE, NOTA_SEGUNDO_SEMESTRE, NOME_PROFESSOR, NUMERO_DA_SALA);
        public static final AlunoDTO ALUNODTO = new AlunoDTO(null, NOME, IDADE, NOTA_PRIMEIRO_SEMESTRE, NOTA_SEGUNDO_SEMESTRE, NOME_PROFESSOR, NUMERO_DA_SALA);
    }
