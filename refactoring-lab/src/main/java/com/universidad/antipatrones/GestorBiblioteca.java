package com.universidad.antipatrones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*
 * Analisis del God Object:
 *
 * La clase GestorBiblioteca tiene varias razones para cambiar porque concentra
 * diferentes responsabilidades del sistema en una sola clase.
 *
 * Responsabilidad 1: Gestion del catalogo de libros.
 * Permite agregar libros, buscar libros y listar libros disponibles.
 *
 * Responsabilidad 2: Gestion de socios.
 * Permite registrar socios, validar el email y verificar si un socio existe.
 *
 * Responsabilidad 3: Gestion de prestamos.
 * Permite realizar prestamos, cambiar la disponibilidad del libro y devolver libros.
 *
 * Responsabilidad 4: Generacion de reportes del sistema.
 * Permite imprimir el resumen general de la biblioteca.
 */

public class GestorBiblioteca {

    // ---- Datos del sistema (3 dominios mezclados) ----
    private List<String[]> libros = new ArrayList<>();   // [id, titulo, autor, disponible]
    private List<String[]> socios = new ArrayList<>();   // [id, nombre, email]
    private List<String[]> prestamos = new ArrayList<>();// [libroId, socioId, fecha]

    // ---- Responsabilidad 1: Gestion de libros ----
    public void agregarLibro(String id, String titulo, String autor) {
        libros.add(new String[]{id, titulo, autor, "true"});
        System.out.println("Libro agregado: " + titulo);
    }

    public Optional<String[]> buscarLibro(String id) {
        return libros.stream().filter(l -> l[0].equals(id)).findFirst();
    }

    public List<String[]> listarLibrosDisponibles() {
        return libros.stream().filter(l -> l[3].equals("true")).toList();
    }

    // ---- Responsabilidad 2: Gestion de socios ----
    public void registrarSocio(String id, String nombre, String email) {
        if (!email.contains("@")) {
            System.out.println("Email invalido");
            return;
        }
        socios.add(new String[]{id, nombre, email});
        System.out.println("Socio registrado: " + nombre);
    }

    public boolean socioExiste(String id) {
        return socios.stream().anyMatch(s -> s[0].equals(id));
    }

    // ---- Responsabilidad 3: Gestion de prestamos ----
    public void realizarPrestamo(String libroId, String socioId) {
        Optional<String[]> libro = buscarLibro(libroId);
        if (libro.isEmpty()) {
            System.out.println("Libro no encontrado");
            return;
        }
        if (!libro.get()[3].equals("true")) {
            System.out.println("Libro no disponible");
            return;
        }
        if (!socioExiste(socioId)) {
            System.out.println("Socio no registrado");
            return;
        }
        libro.get()[3] = "false";
        prestamos.add(new String[]{libroId, socioId, "2026-03-17"});
        System.out.println("Prestamo realizado: libro " + libroId + " a socio " + socioId);
    }

    public void devolverLibro(String libroId) {
        Optional<String[]> libro = buscarLibro(libroId);
        libro.ifPresent(l -> {
            l[3] = "true";
            System.out.println("Libro devuelto: " + l[1]);
        });
    }

    // ---- Responsabilidad 4: Reportes ----
    public void imprimirReporteCompleto() {
        System.out.println("=== REPORTE BIBLIOTECA ===");
        System.out.println("Libros registrados: " + libros.size());
        System.out.println("Libros disponibles: " + listarLibrosDisponibles().size());
        System.out.println("Socios registrados: " + socios.size());
        System.out.println("Prestamos activos: " + prestamos.size());
        System.out.println("==========================");
    }
}
