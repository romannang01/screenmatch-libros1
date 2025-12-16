package com.aluracursos.screenmatch_libros.principal;

import com.aluracursos.screenmatch_libros.modelo.Idiomas;
import com.aluracursos.screenmatch_libros.modelo.Libro;
import com.aluracursos.screenmatch_libros.repositorio.LibrosRepositorio;
import com.aluracursos.screenmatch_libros.servicio.LibreriaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    
    private Scanner teclado = new Scanner(System.in);


    private LibreriaService servicio;
    private List<Libro> libros;

    private Optional<Libro> libroBuscado;
    private LibrosRepositorio libroRepositorio;

    public Principal(LibreriaService servicio) {
        this.servicio = servicio;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            var menu = """
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en determinado año
                    5- Listar libros por idioma
                    
                    0- Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();



            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el título del libro que desea buscar:");
                    String titulo = teclado.nextLine(); // lee el título
                    servicio.buscarLibroPorTitulo(titulo);
                case 2:
                    servicio.listarLibros();
                    break;
                case 3:
                    servicio.listarAutores();
                    break;
                case 4:
                    System.out.println("Escribe un año determinado: ");
                    int anio = teclado.nextInt();
                    servicio.listarAutorPorDeterminadoAnio(anio);
                    break;
                case 5:
                    System.out.println("Escribe el idioma: ");
                    var input = teclado.nextLine();
                    try {
                        var idiomaEnum = Idiomas.fromNombre(input); // mapea "español" a ESPAÑOL
                        var libros = servicio.listarLibrosPorIdioma(idiomaEnum);
                        if (libros.isEmpty()) {
                            System.out.println("No se encontraron libros en ese idioma.");
                        } else {
                            libros.forEach(System.out::println);
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Idioma no encontrado. Intenta con otro.");
                    }
                    break;

            }
        }

    }


}
