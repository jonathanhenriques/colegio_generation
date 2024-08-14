CREATE TABLE TB_ALUNO(
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    idade INTEGER NOT NULL,
    nota_primeiro_semestre INTEGER NOT NULL,
    nota_segundo_semestre INTEGER NOT NULL,
    nome_professor TEXT NOT NULL,
    numero_da_sala INTEGER NOT NULL

);






INSERT INTO TB_ALUNO(nome, idade ,nota_primeiro_semestre,
 nota_segundo_semestre ,nome_professor, numero_da_sala) VALUES (
'Jonathan Henrique Da Silva', 27, 9.5, 9.9, 'Professor Antonio', 71
);