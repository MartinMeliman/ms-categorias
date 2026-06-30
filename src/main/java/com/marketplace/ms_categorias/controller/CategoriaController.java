package com.marketplace.ms_categorias.controller;

import com.marketplace.ms_categorias.model.Categoria;
import com.marketplace.ms_categorias.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Categorías", description = "Gestión de categorías y subcategorías de productos del marketplace EcoTrade")
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(summary = "Listar todas las categorías",
               description = "Retorna todas las categorías incluyendo las inactivas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public List<Categoria> obtenerTodas() {
        return categoriaService.obtenerTodas();
    }

    @Operation(summary = "Listar categorías activas",
               description = "Retorna solo las categorías activas del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de categorías activas")
    })
    @GetMapping("/activas")
    public List<Categoria> obtenerActivas() {
        return categoriaService.obtenerActivas();
    }

    @Operation(summary = "Listar categorías raíz",
               description = "Retorna las categorías que no tienen categoría padre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de categorías raíz")
    })
    @GetMapping("/raices")
    public List<Categoria> obtenerRaices() {
        return categoriaService.obtenerRaices();
    }

    @Operation(summary = "Listar subcategorías",
               description = "Retorna todas las subcategorías de una categoría padre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de subcategorías"),
        @ApiResponse(responseCode = "404", description = "Categoría padre no encontrada")
    })
    @GetMapping("/{id}/subcategorias")
    public List<Categoria> subcategorias(
            @Parameter(description = "ID de la categoría padre") @PathVariable Long id) {
        return categoriaService.obtenerSubcategorias(id);
    }

    @Operation(summary = "Buscar categorías por nombre",
               description = "Busca categorías cuyo nombre contenga el texto indicado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de categorías encontradas")
    })
    @GetMapping("/buscar")
    public List<Categoria> buscar(
            @Parameter(description = "Nombre o parte del nombre a buscar") @RequestParam String nombre) {
        return categoriaService.buscarPorNombre(nombre);
    }

    @Operation(summary = "Obtener categoría por ID",
               description = "Busca una categoría por su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(
            @Parameter(description = "ID de la categoría") @PathVariable Long id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva categoría",
               description = "Registra una nueva categoría. El nombre debe ser único en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Nombre duplicado o datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.guardar(c));
    }

    @Operation(summary = "Actualizar categoría",
               description = "Actualiza los datos de una categoría existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(
            @Parameter(description = "ID de la categoría") @PathVariable Long id,
            @Valid @RequestBody Categoria datos) {
        return categoriaService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar categoría",
               description = "Elimina una categoría del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la categoría a eliminar") @PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}