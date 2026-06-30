package com.marketplace.ms_categorias;

import com.marketplace.ms_categorias.model.Categoria;
import com.marketplace.ms_categorias.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.marketplace.ms_categorias.service.CategoriaService;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para CategoriaService.
 * Patrón Given/When/Then con Mockito (sin BD real).
 */
@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock private CategoriaRepository categoriaRepository;
    @InjectMocks private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Tecnologia");
        categoria.setDescripcion("Productos tecnologicos");
        categoria.setActiva(true);
    }

    @Test
    @DisplayName("obtenerTodas: debería retornar todas las categorías")
    void shouldReturnAllCategorias() {
        // GIVEN
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        // WHEN
        List<Categoria> resultado = categoriaService.obtenerTodas();
        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("obtenerActivas: debería retornar solo categorías activas")
    void shouldReturnActiveCategorias() {
        // GIVEN
        when(categoriaRepository.findAllActivas()).thenReturn(List.of(categoria));
        // WHEN
        List<Categoria> resultado = categoriaService.obtenerActivas();
        // THEN
        assertNotNull(resultado);
        assertTrue(resultado.get(0).isActiva());
    }

    @Test
    @DisplayName("obtenerPorId: debería retornar la categoría cuando existe")
    void shouldReturnCategoriaById() {
        // GIVEN
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        // WHEN
        Optional<Categoria> resultado = categoriaService.obtenerPorId(1L);
        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("Tecnologia", resultado.get().getNombre());
    }

    @Test
    @DisplayName("guardar: debería crear la categoría cuando el nombre no existe")
    void shouldSaveCategoriaSuccessfully() {
        // GIVEN
        when(categoriaRepository.existsByNombre("Tecnologia")).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        // WHEN
        Categoria resultado = categoriaService.guardar(categoria);
        // THEN
        assertNotNull(resultado);
        assertEquals("Tecnologia", resultado.getNombre());
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    @DisplayName("guardar: debería lanzar excepción cuando el nombre ya existe")
    void shouldThrowWhenNombreDuplicated() {
        // GIVEN — regla de negocio: nombre único
        when(categoriaRepository.existsByNombre("Tecnologia")).thenReturn(true);
        // WHEN + THEN
        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> categoriaService.guardar(categoria));
        assertTrue(ex.getMessage().contains("nombre"));
        verify(categoriaRepository, never()).save(any());
    }

    @Test
    @DisplayName("obtenerRaices: debería retornar categorías sin padre")
    void shouldReturnRootCategorias() {
        // GIVEN
        when(categoriaRepository.findByCategoriaPadreIdIsNull()).thenReturn(List.of(categoria));
        // WHEN
        List<Categoria> resultado = categoriaService.obtenerRaices();
        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("actualizar: debería actualizar la categoría cuando existe")
    void shouldUpdateCategoria() {
        // GIVEN
        Categoria datos = new Categoria();
        datos.setNombre("Electronica");
        datos.setDescripcion("Nueva desc");
        datos.setActiva(true);
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        // WHEN
        Optional<Categoria> resultado = categoriaService.actualizar(1L, datos);
        // THEN
        assertTrue(resultado.isPresent());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }
}
