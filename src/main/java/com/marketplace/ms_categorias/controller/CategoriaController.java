package com.marketplace.ms_categorias.controller;
import com.marketplace.ms_categorias.model.Categoria;
import com.marketplace.ms_categorias.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/categorias") @RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping public List<Categoria> obtenerTodas() { return categoriaService.obtenerTodas(); }
    @GetMapping("/activas") public List<Categoria> obtenerActivas() { return categoriaService.obtenerActivas(); }
    @GetMapping("/raices")  public List<Categoria> obtenerRaices()  { return categoriaService.obtenerRaices(); }
    @GetMapping("/{id}/subcategorias") public List<Categoria> subcategorias(@PathVariable Long id) { return categoriaService.obtenerSubcategorias(id); }
    @GetMapping("/buscar")  public List<Categoria> buscar(@RequestParam String nombre) { return categoriaService.buscarPorNombre(nombre); }
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.guardar(c));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @Valid @RequestBody Categoria datos) {
        return categoriaService.actualizar(id, datos).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { categoriaService.eliminar(id); return ResponseEntity.noContent().build(); }
}
