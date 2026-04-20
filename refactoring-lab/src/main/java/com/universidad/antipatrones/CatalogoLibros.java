package com.universidad.antipatrones;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CatalogoLibros {
    private final List<Libro> libros = new ArrayList<>();

    public void agregar(Libro libro) {
        libros.add(libro);
        System.out.println("Libro agregado: " + libro.getTitulo());
    }

    public Optional<Libro> buscarPorId(String id) {
        return libros.stream().filter(l -> l.getId().equals(id)).findFirst();
    }

    public List<Libro> listarDisponibles() {
        return libros.stream().filter(Libro::isDisponible).toList();
    }

    public int totalLibros() {
        return libros.size();
    }
}
