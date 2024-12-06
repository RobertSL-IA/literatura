package com.aluracursos.desafio.model;

import jakarta.persistence.*;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    private String idioma;

    @Column(name = "fecha_nacimiento_autor")
    private Integer fechaNacimientoAutor;

    @Column(name = "fecha_fallecimiento_autor")
    private Integer fechaFallecimientoAutor;

    private Double numeroDeDescargas;

    public Libro() {
    }

    public Libro(String titulo, String autor, String idioma, Integer fechaNacimientoAutor, Integer fechaFallecimientoAutor, Double numeroDeDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.fechaNacimientoAutor = fechaNacimientoAutor;
        this.fechaFallecimientoAutor = fechaFallecimientoAutor;
        this.numeroDeDescargas = numeroDeDescargas;
    }

    // Getters y setters
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getFechaNacimientoAutor() {
        return fechaNacimientoAutor;
    }

    public void setFechaNacimientoAutor(Integer fechaNacimientoAutor) {
        this.fechaNacimientoAutor = fechaNacimientoAutor;
    }

    public Integer getFechaFallecimientoAutor() {
        return fechaFallecimientoAutor;
    }

    public void setFechaFallecimientoAutor(Integer fechaFallecimientoAutor) {
        this.fechaFallecimientoAutor = fechaFallecimientoAutor;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "Libro{"
                + "id=" + id
                + ", titulo='" + titulo + '\''
                + ", autor='" + autor + '\''
                + ", idioma='" + idioma + '\''
                + ", fechaNacimientoAutor=" + fechaNacimientoAutor
                + ", fechaFallecimientoAutor=" + fechaFallecimientoAutor
                + ", numeroDeDescargas=" + numeroDeDescargas
                + '}';
    }
}
