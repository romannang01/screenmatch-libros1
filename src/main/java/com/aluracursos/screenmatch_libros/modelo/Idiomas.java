package com.aluracursos.screenmatch_libros.modelo;

import jakarta.persistence.Id;

public enum Idiomas {
    ESPAÑOL("es"),
    INGLÉS("en"),
    FRANCÉS("fr"),
    ALEMÁN("de"),
    HUNGARO("hu"),
    FINLANDÉS("fi"),
    PORTUGUES("pt"),
    RUSO("ru"),
    ITALIANO("it");

    private String codigo;


    Idiomas(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }



    public static Idiomas fromCodigo(String codigo) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.getCodigo().equalsIgnoreCase(codigo)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + codigo);
    }


    public static Idiomas fromNombre(String nombre) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.name().equalsIgnoreCase(nombre)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + nombre);
    }


}
