package br.com.gva.quefominha.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import br.com.gva.quefominha.domain.dto.restaurant.RestaurantSavedDto;
import br.com.gva.quefominha.domain.entity.Category;
import br.com.gva.quefominha.domain.entity.OpeningHours;
import br.com.gva.quefominha.domain.entity.Product;
import br.com.gva.quefominha.domain.entity.Restaurant;
import br.com.gva.quefominha.domain.entity.Review;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.CategoryRepository;
import br.com.gva.quefominha.repositories.RestaurantRepository;
import br.com.gva.quefominha.service.RestaurantService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Getter @Autowired private RestaurantRepository restaurantRepository;
    @Getter @Autowired private ProductServiceImpl productServiceImpl;
    @Getter @Autowired private ReviewServiceImpl reviewServiceImpl;
    @Getter @Autowired private MongoTemplate mongoTemplate;
    @Getter @Autowired private CategoryRepository categoryRepository;

    @Override
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getRestaurantRepository().findAll();
    }

    public List<Product> findProductByRestaurantId(String restaurantId) {
        return this.productServiceImpl.findProductByRestaurantId(restaurantId);
    }

    public List<Review> findReviewByRestaurantId(String restaurantId) {
        return this.reviewServiceImpl.findReviewByRestaurantId(restaurantId);
    }

    @Override
    public <DTO> DTO findById(String id) {
        Restaurant restaurant = localFindById(id);
        loadOpeningHours(restaurant);
        loadCategories(restaurant);
        return (DTO) populateDto(restaurant, new RestaurantSavedDto());
    }

    private void loadOpeningHours(Restaurant restaurant) {
        try {
            Query query = Query.query(
                Criteria.where("restaurant.$id").is(new ObjectId(restaurant.getId()))
            );
            List<OpeningHours> hours = mongoTemplate.find(query, OpeningHours.class);
            restaurant.setOpeningHours(hours != null ? hours : new ArrayList<>());
        } catch (Exception e) {
            restaurant.setOpeningHours(new ArrayList<>());
        }
    }

    /**
     * Lê os documentos brutos da collection "category" usando o driver nativo
     * do MongoDB (sem passar pelo Spring Data / @DBRef resolver).
     *
     * Isso evita o problema do Spring Data 3.x que não suporta
     * filtro via "array.$id" para campos @DBRef.
     *
     * O filtro é feito diretamente no BSON: busca documentos onde
     * o array "restaurants" contém um DBRef cujo $id é o ObjectId do restaurante.
     */
    private void loadCategories(Restaurant restaurant) {
        List<Category> result = new ArrayList<>();
        try {
            ObjectId restaurantObjectId = new ObjectId(restaurant.getId());

            // Acessa a collection diretamente via driver nativo
            MongoCollection<Document> collection =
                mongoTemplate.getDb().getCollection("category");

            // Query nativa: busca pelo $id dentro do array de DBRefs
            Document filter = new Document("restaurants",
                new Document("$elemMatch",
                    new Document("$id", restaurantObjectId)
                )
            );

            try (MongoCursor<Document> cursor = collection.find(filter).iterator()) {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    String categoryId = doc.getObjectId("_id").toHexString();
                    // Carrega a entidade Category pelo id para ter o objeto completo
                    categoryRepository.findById(categoryId).ifPresent(result::add);
                }
            }
        } catch (Exception e) {
            // fallback silencioso — categoria não crítica
        }
        restaurant.setCategories(result);
    }

    private Restaurant localFindById(String id) {
        return getRestaurantRepository().findById(id).orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
        Restaurant restaurant = new Restaurant();
        return (SAVED) populateDto(
            getRestaurantRepository().save(populateEntity(dto, restaurant)),
            RestaurantSavedDto.builder().build()
        );
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Restaurant restaurant = localFindById(id);
        return (SAVED) populateDto(
            getRestaurantRepository().save(populateEntity(dto, restaurant)),
            RestaurantSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) getRestaurantRepository().deleteById(id);
    }

    @Override
    public boolean existsItem(String id) {
        return getRestaurantRepository().existsById(id);
    }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        return null;
    }
}
