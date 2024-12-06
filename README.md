# Challenge LiterAlura: Catálogo de Libros

**LiterAlura** es un catálogo de libros interactivo desarrollado en Java que permite a los usuarios buscar, consultar y gestionar información sobre libros y autores a través de la consola. Este proyecto consume una API de libros, manipula datos en formato JSON, los almacena en una base de datos, y proporciona múltiples opciones para interactuar con los usuarios.

## Descripción

El objetivo principal de **LiterAlura** es ofrecer un catálogo de libros interactivo a través de la consola, donde los usuarios pueden realizar consultas y obtener información relevante. Las consultas se realizan a través de una API de libros, y los datos se almacenan y gestionan mediante una base de datos relacional.

### Funcionalidades

El sistema ofrece al usuario las siguientes opciones de interacción a través de un menú en la consola:

1. **Buscar libros por título**: Permite al usuario buscar libros ingresando un título.
3. **Ver todos los libros en el catálogo**: Muestra una lista de todos los libros almacenados en la base de datos.
4. **Ver detalles de un libro**: Permite al usuario obtener información detallada sobre un libro específico.

### Herramientas y Tecnologías Utilizadas

- **Java 11+**: Lenguaje de programación utilizado para el desarrollo.
- **Maven**: Gestión de dependencias y construcción del proyecto.
- **API de libros (OpenLibrary)**: API RESTful utilizada para obtener información sobre libros.
- **Gson**: Biblioteca para la manipulación de datos JSON.
- **JDBC**: Tecnología utilizada para interactuar con la base de datos.

### Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes:

- **`model`**: Clases que representan los datos del catálogo (Libro, Autor).
- **`service`**: Lógica de negocio que maneja las interacciones con la API, la base de datos y las consultas del usuario.
- **`repository`**: Interacciones con la base de datos (CRUD de libros y autores).
- **`controller`**: Controladores que manejan la entrada del usuario y muestran los resultados en consola.

Ejemplo de Interacción
Elige la opcion a traves de su numero:
                1 - Buscar libro por titulo
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado ano
                5 - Listar libros por idioma
                0 - Salir
Ingrese el número de la opción: 
