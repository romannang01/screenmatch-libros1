package com.aluracursos.screenmatch_libros.dto;

import com.aluracursos.screenmatch_libros.modelo.Autor;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double cantidadDescargas,
        @JsonAlias("authors") List<AutorDTO> autor

) { }
