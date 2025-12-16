package com.aluracursos.screenmatch_libros.servicio;

import com.aluracursos.screenmatch_libros.dto.AutorDTO;
import com.aluracursos.screenmatch_libros.modelo.Autor;
import com.aluracursos.screenmatch_libros.repositorio.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio autorRepositorio;

    public Autor buscarOCrearAutor(String nombre, String fechaNacimiento, String fechaMuerte) {
        // Buscar autor por nombre
        Optional<Autor> autorExistente = autorRepositorio.findByNombreIgnoreCase(nombre);

        if (autorExistente.isPresent()) {
            return autorExistente.get();
        } else {
            // Crear AutorDTO temporal
            AutorDTO autorDTO = new AutorDTO(nombre, fechaNacimiento, fechaMuerte);
            // Crear autor usando el constructor que tenés
            Autor autor = new Autor(autorDTO);
            return autorRepositorio.save(autor);
        }
    }
}
