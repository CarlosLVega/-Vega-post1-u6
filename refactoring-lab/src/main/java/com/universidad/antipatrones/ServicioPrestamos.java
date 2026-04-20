package com.universidad.antipatrones;

import java.util.ArrayList;
import java.util.List;

public class ServicioPrestamos {
    private final CatalogoLibros catalogo;
    private final RegistroSocios registro;
    private final List<String[]> prestamosActivos = new ArrayList<>();

    public ServicioPrestamos(CatalogoLibros catalogo, RegistroSocios registro) {
        this.catalogo = catalogo;
        this.registro = registro;
    }

    public void prestar(String libroId, String socioId) {
        Libro libro = catalogo.buscarPorId(libroId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado: " + libroId));

        if (!libro.isDisponible()) {
            throw new IllegalStateException("Libro no disponible: " + libro.getTitulo());
        }

        if (!registro.existe(socioId)) {
            throw new IllegalArgumentException("Socio no registrado: " + socioId);
        }

        libro.setDisponible(false);
        prestamosActivos.add(new String[]{libroId, socioId});
        System.out.println("Prestamo realizado: " + libro.getTitulo() + " -> socio " + socioId);
    }

    public void devolver(String libroId) {
        catalogo.buscarPorId(libroId).ifPresent(l -> {
            l.setDisponible(true);
            prestamosActivos.removeIf(p -> p[0].equals(libroId));
            System.out.println("Libro devuelto: " + l.getTitulo());
        });
    }

    public int totalPrestamosActivos() {
        return prestamosActivos.size();
    }
}
