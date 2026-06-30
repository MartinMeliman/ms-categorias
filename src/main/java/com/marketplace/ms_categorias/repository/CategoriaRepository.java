package com.marketplace.ms_categorias.repository;
import com.marketplace.ms_categorias.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByCategoriaPadreIdIsNull();
    List<Categoria> findByCategoriaPadreId(Long padreId);
    @Query("SELECT c FROM Categoria c WHERE c.activa = true") List<Categoria> findAllActivas();
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Categoria> buscarPorNombre(@Param("nombre") String nombre);
    boolean existsByNombre(String nombre);
}
