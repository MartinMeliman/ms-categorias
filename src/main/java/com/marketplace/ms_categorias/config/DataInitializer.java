package com.marketplace.ms_categorias.config;
import com.marketplace.ms_categorias.model.Categoria;
import com.marketplace.ms_categorias.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j @Component @RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CategoriaRepository repository;
    @Override
    public void run(String... args) {
        if (repository.count() > 0) { log.info(">>> Categorias ya cargadas."); return; }
        log.info(">>> Cargando categorias iniciales...");
        Categoria elec  = repository.save(new Categoria(null,"Electronica","Productos electronicos",null,true));
        Categoria ropa  = repository.save(new Categoria(null,"Ropa","Vestuario en general",null,true));
        Categoria hogar = repository.save(new Categoria(null,"Hogar","Articulos para el hogar",null,true));
        repository.save(new Categoria(null,"Computadores","Laptops y PCs",elec.getId(),true));
        repository.save(new Categoria(null,"Celulares","Smartphones",elec.getId(),true));
        repository.save(new Categoria(null,"Ropa Hombre","Vestuario masculino",ropa.getId(),true));
        repository.save(new Categoria(null,"Muebles","Muebles para el hogar",hogar.getId(),true));
        log.info(">>> 7 categorias cargadas OK.");
    }
}
