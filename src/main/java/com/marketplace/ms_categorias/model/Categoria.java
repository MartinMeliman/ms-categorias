package com.marketplace.ms_categorias.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "categorias")
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank(message = "El nombre no puede estar vacio") @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100) private String nombre;
    @Column(length = 255) private String descripcion;
    // NULL = categoria raiz, con valor = subcategoria
    @Column(name = "categoria_padre_id") private Long categoriaPadreId;
    @Column(nullable = false) private boolean activa = true;
}
