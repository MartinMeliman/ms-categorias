package com.marketplace.ms_categorias.service;
import com.marketplace.ms_categorias.model.Categoria;
import com.marketplace.ms_categorias.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j @Service @RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas()  { return categoriaRepository.findAll(); }
    public List<Categoria> obtenerActivas(){ return categoriaRepository.findAllActivas(); }
    public List<Categoria> obtenerRaices() { return categoriaRepository.findByCategoriaPadreIdIsNull(); }
    public List<Categoria> obtenerSubcategorias(Long padreId){ return categoriaRepository.findByCategoriaPadreId(padreId); }
    public List<Categoria> buscarPorNombre(String nombre){ return categoriaRepository.buscarPorNombre(nombre); }
    public Optional<Categoria> obtenerPorId(Long id){ return categoriaRepository.findById(id); }

    public Categoria guardar(Categoria c) {
        if (categoriaRepository.existsByNombre(c.getNombre()))
            throw new RuntimeException("Ya existe una categoria con el nombre: " + c.getNombre());
        log.info("Guardando categoria: {}", c.getNombre());
        return categoriaRepository.save(c);
    }

    public Optional<Categoria> actualizar(Long id, Categoria datos) {
        return categoriaRepository.findById(id).map(c -> {
            c.setNombre(datos.getNombre()); c.setDescripcion(datos.getDescripcion());
            c.setCategoriaPadreId(datos.getCategoriaPadreId()); c.setActiva(datos.isActiva());
            return categoriaRepository.save(c);
        });
    }

    public void eliminar(Long id) { categoriaRepository.deleteById(id); }
}
