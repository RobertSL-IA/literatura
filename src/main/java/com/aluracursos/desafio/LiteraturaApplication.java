package com.aluracursos.desafio;

import com.aluracursos.desafio.principal.Principal;
import com.aluracursos.desafio.repository.LibroRepository;
import com.aluracursos.desafio.service.LibroService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService;

    public static void main(String[] args) {
        SpringApplication.run(LiteraturaApplication.class, args);
    }

    @PostConstruct
    public void init() {
        Principal principal = new Principal(libroRepository, libroService);
        principal.muestraElMenu();
    }
}
