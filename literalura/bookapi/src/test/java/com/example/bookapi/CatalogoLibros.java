package com.example.bookapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookapiApplicationTests {

	@Test
	void contextLoads() {
	}

}
import java.util.Scanner;

public class CatalogoLibros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibroApiClient apiClient = new LibroApiClient();
        Favoritos favoritos = new Favoritos();

        while (true) {
            System.out.println("Bienvenido al Catálogo de Libros Interactivo");
            System.out.println("1. Buscar libros");
            System.out.println("2. Ver detalles del libro");
            System.out.println("3. Agregar libro a favoritos");
            System.out.println("4. Eliminar libro de favoritos");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    buscarLibros(scanner, apiClient);
                    break;
                case 2:
                    verDetallesDelLibro(scanner, apiClient);
                    break;
                case 3:
                    agregarLibroAFavoritos(scanner, apiClient, favoritos);
                    break;
                case 4:
                    eliminarLibroDeFavoritos(scanner, favoritos);
                    break;
                case 5:
                    System.out.println("Hasta luego. ¡Gracias por usar el Catálogo de Libros Interactivo!");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        }
    }

    private static void buscarLibros(Scanner scanner, LibroApiClient apiClient) {
        System.out.print("¿Qué quieres buscar? (Título, Autor, ISBN): ");
        String criterio = scanner.nextLine();
        System.out.print("Ingresa el término de búsqueda: ");
        String termino = scanner.nextLine();

        try {
            var libros = apiClient.buscarLibros(criterio, termino);
            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros.");
            } else {
                System.out.println("Resultados:");
                for (int i = 0; i < libros.size(); i++) {
                    System.out.println((i + 1) + ". " + libros.get(i).getTitulo() + " - " + libros.get(i).getAutor() + " (" + libros.get(i).getAnioPublicacion() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar libros: " + e.getMessage());
        }
    }

    private static void verDetallesDelLibro(Scanner scanner, LibroApiClient apiClient) {
        System.out.print("Ingresa el ID del libro: ");
        String id = scanner.nextLine();

        try {
            var libro = apiClient.obtenerDetallesDelLibro(id);
            System.out.println("Detalles del libro:");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Año de Publicación: " + libro.getAnioPublicacion());
            System.out.println("ISBN: " + libro.getIsbn());
            System.out.println("Descripción: " + libro.getDescripcion());
            System.out.println("Género: " + libro.getGenero());
        } catch (Exception e) {
            System.out.println("Error al obtener detalles del libro: " + e.getMessage());
        }
    }

    private static void agregarLibroAFavoritos(Scanner scanner, LibroApiClient apiClient, Favoritos favoritos) {
        System.out.print("Ingresa el ID del libro: ");
        String id = scanner.nextLine();

        try {
            var libro = apiClient.obtenerDetallesDelLibro(id);
            favoritos.agregarLibro(libro);
            System.out.println("El libro ha sido agregado a tus favoritos.");
        } catch (Exception e) {
            System.out.println("Error al agregar el libro a favoritos: " + e.getMessage());
        }
    }

    private static void eliminarLibroDeFavoritos(Scanner scanner, Favoritos favoritos) {
        System.out.print("Ingresa el ID del libro: ");
        String id = scanner.nextLine();

        if (favoritos.eliminarLibro(id)) {
            System.out.println("El libro ha sido eliminado de tus favoritos.");
        } else {
            System.out.println("El libro no se encuentra en tus favoritos.");
        }
    }
}