package com.aluracursos.desafio.service;

import com.aluracursos.desafio.model.Libro;
import com.aluracursos.desafio.repository.LibroRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public Optional<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }
}
