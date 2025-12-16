package com.aluracursos.screenmatch_libros.modelo;

import com.aluracursos.screenmatch_libros.dto.LibroDTO;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idiomas;

    private Double cantidadDescargas;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro(){}


    public Set<Idiomas> getIdiomasEnum() {
        if (idiomas == null || idiomas.isEmpty()) return new HashSet<>();
        return Arrays.stream(idiomas.split(","))
                .map(String::trim)
                .map(Idiomas::fromCodigo)
                .collect(Collectors.toSet());
    }

    // Convierte Set<Idiomas> a String para almacenar en la columna
    public void setIdiomasEnum(Set<Idiomas> idiomasEnum) {
        this.idiomas = idiomasEnum.stream()
                .map(Idiomas::getCodigo)
                .collect(Collectors.joining(","));
    }

    public Libro(LibroDTO libro) {

        this.titulo = libro.titulo();
        this.idiomas = String.join(", ", libro.idiomas());
        this.cantidadDescargas = libro.cantidadDescargas();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Double cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "LIBROS" +
                "\n************\n" +
                "Título ='" + titulo +
                "\nIdiomas ='" + idiomas  +
                "\nCantidad de descargas =" + cantidadDescargas +
                "\n************\n";
    }
}
