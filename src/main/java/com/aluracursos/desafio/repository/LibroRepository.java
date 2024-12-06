package com.aluracursos.desafio.repository;

import com.aluracursos.desafio.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTitulo(String titulo);

}
