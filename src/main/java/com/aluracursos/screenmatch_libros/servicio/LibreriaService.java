package com.aluracursos.screenmatch_libros.servicio;

import com.aluracursos.screenmatch_libros.dto.AutorDTO;
import com.aluracursos.screenmatch_libros.dto.DatosGutendex;
import com.aluracursos.screenmatch_libros.dto.LibroDTO;
import com.aluracursos.screenmatch_libros.modelo.Autor;
import com.aluracursos.screenmatch_libros.modelo.Idiomas;
import com.aluracursos.screenmatch_libros.modelo.Libro;
import com.aluracursos.screenmatch_libros.repositorio.AutorRepositorio;
import com.aluracursos.screenmatch_libros.repositorio.LibrosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibreriaService {

    private final ConvierteDatos conversor;
    private final ConsumoAPI consumoAPI;

    @Autowired
    private LibrosRepositorio librosRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private AutorService autorService;
    public LibreriaService(LibrosRepositorio librosRepositorio){
        this.consumoAPI = new ConsumoAPI();
        this.conversor = new ConvierteDatos();
    }



    private static final String URL_BASE = "https://gutendex.com/books/?search=";


    public List<LibroDTO> obtenerDatos(List<Libro> libros) {
        return libros.stream()
                .map(libro -> new LibroDTO(
                        libro.getId(),
                        libro.getTitulo(),
                        Arrays.asList(libro.getIdiomas().split(", ")),
                        libro.getCantidadDescargas(),
                        List.of(new AutorDTO(
                                libro.getAutor().getNombre(),
                                libro.getAutor().getFechaNacimiento() != null ? libro.getAutor().getFechaNacimiento().toString() : null,
                                libro.getAutor().getFechaMuerte() != null ? libro.getAutor().getFechaMuerte().toString() : null
                        ))))
                .collect(Collectors.toList());
    }

    public List<AutorDTO> obtenerDatosAutor(List<Autor> autores) {
        return autores.stream()
                .map(autor -> new AutorDTO(
                        autor.getNombre(),
                        autor.getFechaNacimiento().toString(),
                        autor.getFechaMuerte() != null ? autor.getFechaMuerte().toString() : null
                )).collect(Collectors.toList());
    }

    public void buscarLibroPorTitulo(String nombreLibro) {
        String url = URL_BASE + nombreLibro.replace(" ", "+");
        String json = consumoAPI.obtenerDatos(url);

        DatosGutendex respuesta = conversor.obtenerDatos(json, DatosGutendex.class);

        if (respuesta.resultados().isEmpty()) return;

        LibroDTO libroDTO = respuesta.resultados().get(0);


        String tituloPrincipal = respuesta.resultados().get(0).titulo();


        Optional<Libro> libroExistente = librosRepositorio.findByTituloIgnoreCase(tituloPrincipal);


        Set<String> idiomasConcatenados = respuesta.resultados().stream()
                .flatMap(l -> l.idiomas().stream())  // l.idiomas() del libro actual
                .collect(Collectors.toSet());



        List<AutorDTO> autoresDTO = libroDTO.autor(); // método según tu DTO
        String nombreAutor;
        String fechaNacimiento = null;
        String fechaMuerte = null;

        if (autoresDTO != null && !autoresDTO.isEmpty()) {
            AutorDTO autorDTO = autoresDTO.get(0);
            nombreAutor = autorDTO.nombre();
            fechaNacimiento = autorDTO.fechaNacimiento();
            fechaMuerte = autorDTO.fechaMuerte();
        } else {
            nombreAutor = "Desconocido";
        }


        Autor autor = autorService.buscarOCrearAutor(nombreAutor, fechaNacimiento, fechaMuerte);

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();


            Set<String> idiomasActuales = new HashSet<>(Arrays.asList(libro.getIdiomas().split(", ")));
            idiomasActuales.addAll(idiomasConcatenados);
            libro.setIdiomas(String.join(", ", idiomasActuales));


            libro.setAutor(autor);

            librosRepositorio.save(libro);
        } else {

            Libro nuevoLibro = new Libro(libroDTO);
            nuevoLibro.setIdiomas(String.join(", ", idiomasConcatenados));
            nuevoLibro.setAutor(autor);

            librosRepositorio.save(nuevoLibro);
        }
    }

    public void listarLibros() {
        librosRepositorio.findAll().forEach(libro -> {

            String idiomasOriginal = libro.getIdiomas();

            String idiomasEnum = Arrays.stream(idiomasOriginal.split(","))
                    .map(String::trim)
                    .map(codigo -> {
                        try {
                            return Idiomas.fromCodigo(codigo).name();
                        } catch (IllegalArgumentException e) {
                            return null; // ignoramos idiomas desconocidos
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(", "));

            if (idiomasEnum.isBlank()) {
                idiomasEnum = "No disponible";
            }

            libro.setIdiomas(idiomasEnum);
            System.out.println(libro);
            libro.setIdiomas(idiomasOriginal);
        });
    }


    public void listarAutores() {
        autorRepositorio.findAll().forEach(System.out::println);
    }

    public void listarAutorPorDeterminadoAnio(int anio) {
        autorRepositorio.listarPorFecha(anio).
                forEach(System.out::println);

    }

    // Servicio
    public List<Libro> listarLibrosPorIdioma(Idiomas idioma) {
        // Buscamos por el código del idioma en la columna 'idiomas'
        List<Libro> libros = librosRepositorio.findByIdiomasContainingIgnoreCase(idioma.getCodigo());

        // Convertimos los códigos de cada libro a nombres del Enum
        for (Libro libro : libros) {
            String[] codigos = libro.getIdiomas().split(",\\s*"); // separar por ", "
            Set<String> nombresIdiomas = new HashSet<>();
            for (String codigo : codigos) {
                try {
                    Idiomas i = Idiomas.fromCodigo(codigo); // convierte código a Enum
                    nombresIdiomas.add(i.name()); // devuelve ESPAÑOL, INGLÉS, etc
                } catch (IllegalArgumentException e) {
                    nombresIdiomas.add(codigo); // si no está en Enum, dejamos el código
                }
            }
            libro.setIdiomas(String.join(", ", nombresIdiomas));
        }
        return libros;
    }

}
