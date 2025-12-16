package com.aluracursos.screenmatch_libros.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosGutendex(
        @JsonAlias("results") List<LibroDTO> resultados
) {
}
