package com.aluracursos.desafio.controller;

import com.aluracursos.desafio.model.Libro;
import com.aluracursos.desafio.service.LibroService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping
    public Libro guardarLibro(@RequestBody Libro libro) {
        return libroService.guardarLibro(libro);
    }

    @GetMapping("/{titulo}")
    public Optional<Libro> obtenerLibro(@PathVariable String titulo) {
        return libroService.buscarPorTitulo(titulo);
    }
}
