package com.aluracursos.screenmatch_libros.repositorio;

import com.aluracursos.screenmatch_libros.modelo.Idiomas;
import com.aluracursos.screenmatch_libros.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibrosRepositorio extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloIgnoreCase(String nombreLibro);


    List<Libro> findByIdiomasContainingIgnoreCase(String idioma);

}
