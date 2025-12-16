package com.aluracursos.screenmatch_libros.repositorio;

import com.aluracursos.screenmatch_libros.dto.AutorDTO;
import com.aluracursos.screenmatch_libros.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface AutorRepositorio extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaMuerte IS NULL OR a.fechaMuerte >= :anio)")
    List<Autor> listarPorFecha(@Param("anio")int anio);
}
