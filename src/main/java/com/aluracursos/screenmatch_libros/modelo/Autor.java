package com.aluracursos.screenmatch_libros.modelo;

import com.aluracursos.screenmatch_libros.dto.AutorDTO;
import jakarta.persistence.*;

import java.time.Year;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;
    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;

    public Autor(){}

    public Autor(AutorDTO autor) {

        this.nombre = autor.nombre();
        this.fechaNacimiento = Integer.valueOf(autor.fechaNacimiento());
        this.fechaMuerte = Integer.valueOf(autor.fechaMuerte());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  "AUTORES" +
                "\n**************" +
                "\nNombre: "   + nombre +
                "\nFecha de nacimiento: " + fechaNacimiento +
                "\nFecha de muerte: " + (fechaMuerte != null ? fechaMuerte : "Vivo") +
                "\n***************\n";
    }
}
