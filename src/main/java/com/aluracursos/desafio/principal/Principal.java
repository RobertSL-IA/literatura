package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.Datos;
import com.aluracursos.desafio.model.DatosAutor;
import com.aluracursos.desafio.model.DatosLibros;
import com.aluracursos.desafio.model.Libro;
import com.aluracursos.desafio.repository.LibroRepository;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;
import com.aluracursos.desafio.service.LibroService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Principal {

    @Autowired
    private LibroRepository libroRepository;

    private final LibroService libroService;
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    @Autowired
    public Principal(LibroRepository libroRepository, LibroService libroService) {
        this.libroRepository = libroRepository;
        this.libroService = libroService;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                ---------------- 
                Elige la opcion a traves de su numero:
                1 - Buscar libro por titulo
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado ano
                5 - Listar libros por idioma
                0 - Salir
                """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorAno();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        String tituloLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibros libro = libroBuscado.get();
            String idioma = libro.idiomas().isEmpty() ? "Desconocido" : libro.idiomas().get(0);
            Double numeroDeDescargas = libro.numeroDeDescargas() != null ? libro.numeroDeDescargas() : 0.0;

            System.out.printf("----- LIBRO -----\nTitulo: %s\nAutor: %s\nIdioma: %s\nNumero de descargas: %.2f\n-----------\n",
                    libro.titulo(),
                    libro.autor().stream().map(DatosAutor::nombre).findFirst().orElse("Desconocido"),
                    idioma,
                    numeroDeDescargas);

            Optional<Libro> libroExistente = libroRepository.findByTitulo(libro.titulo());
            if (libroExistente.isPresent()) {
                System.out.println("No se puede registrar el mismo libro mas de una vez");
            } else {
                Libro libroNuevo = new Libro();
                libroNuevo.setTitulo(libro.titulo());
                libroNuevo.setAutor(libro.autor().stream().map(DatosAutor::nombre).findFirst().orElse("Desconocido"));
                libroNuevo.setIdioma(idioma);
                libroNuevo.setNumeroDeDescargas(numeroDeDescargas);

                String fechaNacimiento = libro.autor().stream().map(DatosAutor::fechaDeNacimiento).findFirst().orElse(null);
                String fechaFallecimiento = libro.autor().stream().map(DatosAutor::fechaDeFallecimiento).findFirst().orElse(null);

                libroNuevo.setFechaNacimientoAutor(convertirAAnio(fechaNacimiento));
                libroNuevo.setFechaFallecimientoAutor(convertirAAnio(fechaFallecimiento));

                libroRepository.save(libroNuevo);
                System.out.println("Libro guardado en la base de datos.");
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private Integer convertirAAnio(String fecha) {
        if (fecha == null || fecha.isEmpty()) {
            return null;
        }
        try {
            if (fecha.length() == 4) {
                return Integer.parseInt(fecha);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear el año: " + fecha);
        }
        return null;
    }

    private void mostrarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> {
                System.out.printf("----- LIBRO -----\nTitulo: %s\nAutor: %s\nIdioma: %s\nNumero de descargas: %.2f\n-----------------\n",
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getIdioma(),
                        libro.getNumeroDeDescargas());
            });
        }
    }

    private void mostrarAutoresRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            Map<String, List<Libro>> librosPorAutor = libros.stream()
                    .collect(Collectors.groupingBy(Libro::getAutor));

            librosPorAutor.forEach((autor, librosDelAutor) -> {
                Integer fechaNacimiento = librosDelAutor.stream()
                        .map(Libro::getFechaNacimientoAutor)
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null);

                Integer fechaFallecimiento = librosDelAutor.stream()
                        .map(Libro::getFechaFallecimientoAutor)
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null);

                System.out.println("Nombre: " + autor);
                System.out.println("Fecha de nacimiento: " + (fechaNacimiento != null ? fechaNacimiento : "Desconocida"));
                System.out.println("Fecha de fallecimiento: " + (fechaFallecimiento != null ? fechaFallecimiento : "Desconocida"));

                System.out.println("Libros:");
                librosDelAutor.forEach(libro -> System.out.println("- " + libro.getTitulo()));
                System.out.println(" ");
            });
        }
    }

    private void mostrarAutoresVivosPorAno() {
        System.out.println("Ingrese el anio en que desea buscar autores vivos:");
        int ano = teclado.nextInt();
        teclado.nextLine();

        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            Map<String, List<Libro>> librosPorAutor = libros.stream()
                    .filter(libro -> libro.getFechaNacimientoAutor() != null
                    && libro.getFechaNacimientoAutor() <= ano
                    && (libro.getFechaFallecimientoAutor() == null || libro.getFechaFallecimientoAutor() >= ano))
                    .collect(Collectors.groupingBy(Libro::getAutor));

            if (librosPorAutor.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el anio " + ano + ".");
            } else {
                librosPorAutor.forEach((autor, librosDelAutor) -> {
                    Integer fechaNacimiento = librosDelAutor.stream()
                            .map(Libro::getFechaNacimientoAutor)
                            .filter(Objects::nonNull)
                            .findFirst()
                            .orElse(null);

                    Integer fechaFallecimiento = librosDelAutor.stream()
                            .map(Libro::getFechaFallecimientoAutor)
                            .filter(Objects::nonNull)
                            .findFirst()
                            .orElse(null);

                    System.out.println("Nombre: " + autor);
                    System.out.println("Fecha de nacimiento: " + (fechaNacimiento != null ? fechaNacimiento : "Desconocida"));
                    System.out.println("Fecha de fallecimiento: " + (fechaFallecimiento != null ? fechaFallecimiento : "Desconocida"));

                    System.out.println("Libros:");
                    librosDelAutor.forEach(libro -> System.out.println("- " + libro.getTitulo()));
                    System.out.println(" ");
                });
            }
        }
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar los libros:");
        System.out.println("es- espaniol");
        System.out.println("en- ingles");
        System.out.println("fr- frances");
        System.out.println("pt- portugués");
        String idioma = teclado.nextLine().toLowerCase();

        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.stream()
                    .filter(libro -> libro.getIdioma().equalsIgnoreCase(idioma))
                    .forEach(libro -> {
                        System.out.printf("----- LIBRO -----\n"
                                + "Titulo: %s\n"
                                + "Autor: %s\n"
                                + "Idioma: %s\n"
                                + "Numero de descargas: %.2f\n"
                                + "-----------------\n",
                                libro.getTitulo(),
                                libro.getAutor(),
                                libro.getIdioma(),
                                libro.getNumeroDeDescargas());
                    });
        }
    }

}
