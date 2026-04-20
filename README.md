# Refactoring Lab - Antipatrones de Diseño

## Información del proyecto

**Asignatura:** Patrones de Diseño de Software  
**Unidad:** Unidad 6 - Antipatrones de Diseño  
**Actividad:** Post-Contenido  
**Estudiante:** Carlos Vega  
**Repositorio:** Vega-post1-u6  

## Descripción

Este proyecto corresponde a un laboratorio de refactorización en Java. El objetivo principal es identificar y corregir el antipatrón conocido como **God Object**, aplicando el **Principio de Responsabilidad Única (SRP)**.

Inicialmente, el sistema se implementó usando una clase llamada `GestorBiblioteca`, la cual concentraba varias responsabilidades dentro de una sola clase. Posteriormente, esa clase fue refactorizada y dividida en clases especializadas, cada una con una responsabilidad clara dentro del sistema.

## Antipatrón identificado: God Object

El antipatrón **God Object** aparece cuando una clase concentra demasiadas responsabilidades y conoce o controla gran parte del sistema. Esto hace que el código sea más difícil de mantener, probar, modificar y extender.

En este proyecto, la clase `GestorBiblioteca` era un God Object porque se encargaba al mismo tiempo de:

- Gestionar libros  
- Gestionar socios  
- Gestionar préstamos  
- Generar reportes  

Esto viola el principio SRP, ya que una clase debería tener una sola razón para cambiar.

## Responsabilidades encontradas en `GestorBiblioteca`

Durante el análisis del código inicial, se identificaron cuatro responsabilidades principales:

1. **Gestión del catálogo de libros**  
   Incluye agregar libros, buscar libros y listar libros disponibles.

2. **Gestión de socios**  
   Incluye registrar socios, validar el correo electrónico y verificar si un socio existe.

3. **Gestión de préstamos**  
   Incluye realizar préstamos, cambiar la disponibilidad de los libros y devolver libros.

4. **Generación de reportes del sistema**  
   Incluye imprimir un resumen general con la cantidad de libros, socios y préstamos activos.

## Principio aplicado: SRP

El principio aplicado fue el **SRP - Single Responsibility Principle**.

Este principio establece que una clase debe tener una sola responsabilidad o una sola razón para cambiar. Al aplicar SRP, el sistema queda más organizado, más fácil de entender y más sencillo de mantener.

## Diseño inicial

En el diseño inicial, la clase principal era:

```text
GestorBiblioteca
````

Esta clase manejaba internamente listas de arreglos de texto:

```java
private List<String[]> libros = new ArrayList<>();
private List<String[]> socios = new ArrayList<>();
private List<String[]> prestamos = new ArrayList<>();
```

Este diseño funcionaba, pero tenía **baja cohesión**, ya que mezclaba diferentes responsabilidades en una sola clase.

## Diseño refactorizado

Después de aplicar SRP, el sistema quedó dividido en las siguientes clases:

* Libro
* Socio
* CatalogoLibros
* RegistroSocios
* ServicioPrestamos
* GeneradorReportes

## Clases de dominio

### Libro

Representa un libro dentro del sistema, reemplazando el uso de `String[]`.

**Atributos:**

* id
* titulo
* autor
* disponible

### Socio

Representa un socio registrado.

**Atributos:**

* id
* nombre
* email

## Clases especializadas

### CatalogoLibros

Responsable de la gestión de libros:

* Agregar libros
* Buscar por id
* Listar disponibles
* Contar libros

### RegistroSocios

Responsable de la gestión de socios:

* Registrar socios
* Validar email
* Buscar por id
* Verificar existencia
* Contar socios

### ServicioPrestamos

Responsable de la gestión de préstamos:

* Realizar préstamos
* Verificar disponibilidad
* Verificar socio
* Devolver libros
* Contar préstamos

```java
public ServicioPrestamos(CatalogoLibros catalogo, RegistroSocios registro) {
    this.catalogo = catalogo;
    this.registro = registro;
}
```

### GeneradorReportes

Responsable de los reportes del sistema:

* Total de libros
* Libros disponibles
* Total de socios
* Préstamos activos

```java
public GeneradorReportes(CatalogoLibros catalogo, RegistroSocios registro, ServicioPrestamos prestamos) {
    this.catalogo = catalogo;
    this.registro = registro;
    this.prestamos = prestamos;
}
```

## Cómo ejecutar el proyecto

Compilar:

```bash
mvn compile
```

Ejecutar:

```bash
mvn exec:java
```

Ejecutar en una sola línea:

```bash
mvn compile && mvn exec:java
```

## Salida de la versión inicial

```
Libro agregado: Clean Code
Libro agregado: Design Patterns
Socio registrado: Ana Torres
Prestamo realizado: libro L01 a socio S01
=== REPORTE BIBLIOTECA ===
Libros registrados: 2
Libros disponibles: 1
Socios registrados: 1
Prestamos activos: 1
==========================
Libro devuelto: Clean Code
=== REPORTE BIBLIOTECA ===
Libros registrados: 2
Libros disponibles: 2
Socios registrados: 1
Prestamos activos: 1
==========================
```

## Salida de la versión refactorizada

```
Libro agregado: Clean Code
Libro agregado: Design Patterns
Socio registrado: Ana Torres
Prestamo realizado: Clean Code -> socio S01
=== REPORTE BIBLIOTECA ===
Libros registrados : 2
Libros disponibles : 1
Socios registrados : 1
Prestamos activos  : 1
==========================
Libro devuelto: Clean Code
=== REPORTE BIBLIOTECA ===
Libros registrados : 2
Libros disponibles : 2
Socios registrados : 1
Prestamos activos  : 0
==========================
```

````
