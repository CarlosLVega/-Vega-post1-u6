package com.universidad.antipatrones;

public class GeneradorReportes {
    private final CatalogoLibros catalogo;
    private final RegistroSocios registro;
    private final ServicioPrestamos prestamos;

    public GeneradorReportes(CatalogoLibros catalogo, RegistroSocios registro, ServicioPrestamos prestamos) {
        this.catalogo = catalogo;
        this.registro = registro;
        this.prestamos = prestamos;
    }

    public void imprimirReporteCompleto() {
        System.out.println("=== REPORTE BIBLIOTECA ===");
        System.out.println("Libros registrados : " + catalogo.totalLibros());
        System.out.println("Libros disponibles : " + catalogo.listarDisponibles().size());
        System.out.println("Socios registrados : " + registro.totalSocios());
        System.out.println("Prestamos activos  : " + prestamos.totalPrestamosActivos());
        System.out.println("==========================");
    }
}
