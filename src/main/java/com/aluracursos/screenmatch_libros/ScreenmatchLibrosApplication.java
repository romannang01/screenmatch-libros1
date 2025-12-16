package com.aluracursos.screenmatch_libros;
import com.aluracursos.screenmatch_libros.principal.Principal;
import com.aluracursos.screenmatch_libros.servicio.LibreriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchLibrosApplication implements CommandLineRunner {

    @Autowired
    private LibreriaService service;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchLibrosApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(service);
        principal.mostrarMenu();
    }
}
