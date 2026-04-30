package br.com.gva.quefominha.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Category;
import br.com.gva.quefominha.domain.entity.Restaurant;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    // Busca existente (paginada por nome + categoria)
    Page<Restaurant> findDistinctByNameIgnoreCaseContainingAndCategoriesIn(
            String nome, List<Category> categorias, PageRequest pageRequest);

    // Busca por nome (case-insensitive, contains) — usada pela barra de pesquisa
    List<Restaurant> findByNameIgnoreCaseContaining(String name);
}
