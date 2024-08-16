



INSERT INTO TB_ALUNO(nome, idade ,nota_primeiro_semestre,
 nota_segundo_semestre ,nome_professor, numero_da_sala) VALUES (
'Jonathan Henrique Da Silva', 27, 9.5, 9.9, 'Professor Antonio', 71
);

--
--
--@Parameter(
--            name = "filtro",
--            description = "filtros de busca (opcional)",
--            required = false,
--            schema = @Schema(
--                    type = "string",
--                    example = """
--        {
--            "id": 1,
--            "nome": "Simone Biles",
--            "idade": 27,
--            "notaPrimeiroSemestre": 8,
--            "notaSegundoSemestre": 7,
--            "nomeProfessor": "Professor Silva",
--            "numeroDaSala": 101
--        }
--        """
--            )
--    )
--    @Parameter(
--            name = "pageable",
--            hidden = true
--    )
--
--    @Operation(description = "Busca todos os Alunos com filtros opcionais")
--    @GetMapping(value = "/filtro", produces = "application/json;charset=UTF-8")
--
--    public Page<Aluno> pesquisar(
--            @Parameter(
--                    name = "filtro",
--                    description = "JSON com os filtros de busca (opcional)",
--                    required = false,
--                    schema = @Schema(
--                            type = "string",
--                            example = """
--        {
--            "id": 1,
--            "nome": "Simone Biles",
--            "idade": 27,
--            "notaPrimeiroSemestre": 8,
--            "notaSegundoSemestre": 7,
--            "nomeProfessor": "Professor Silva",
--            "numeroDaSala": 101
--        }
--        """
--                    )
--            ) @RequestParam(required = false) String filtro,
--            @Parameter(
--                    name = "page",
--                    description = "Índice da página (começando em 0). Opcional.",
--                    required = false,
--                    schema = @Schema(type = "integer", defaultValue = "0")
--            ) @RequestParam(required = false) Integer page,
--            @Parameter(
--                    name = "size",
--                    description = "Tamanho da página. Opcional.",
--                    required = false,
--                    schema = @Schema(type = "integer", defaultValue = "10")
--            ) @RequestParam(required = false) Integer size,
--            @Parameter(
--                    name = "sort",
--                    description = "Critérios de ordenação no formato: propriedade ,(asc|desc) ex: nome,asc. Opcional.",
--                    required = false,
--                    schema = @Schema(type = "string", example = "nome,asc")
--            ) @RequestParam(required = false) String sort
--    ) throws JsonProcessingException {
--        ObjectMapper objectMapper = new ObjectMapper();
--
--        AlunoFilter alunoFilter = filtro != null ? objectMapper.readValue(filtro, AlunoFilter.class) : new AlunoFilter();
--
--        // Configura a ordenação padrão
--        Sort sortCriteria = Sort.unsorted();
--
--        if (sort != null && !sort.isEmpty()) {
--            String[] sortParts = sort.split(",");
--            if (sortParts.length > 1) {
--                String sortField = sortParts[0]; // Primeiro valor é o campo
--                Sort.Direction sortDirection = sortParts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC; // Verifica se a direção é "desc"
--                sortCriteria = Sort.by(new Sort.Order(sortDirection, sortField));
--            } else {
--                // Se não houver direção, assume que é asc
--                String sortField = sortParts[0];
--                sortCriteria = Sort.by(new Sort.Order(Sort.Direction.ASC, sortField));
--            }
--        }
--
--        Pageable pageable = PageRequest.of(
--                page != null ? page : 0,
--                size != null ? size : 10,
--                sortCriteria
--        );
--
--        return alunoService.findAll(alunoFilter, pageable);
--    }
