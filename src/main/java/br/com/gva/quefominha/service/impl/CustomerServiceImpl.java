package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.api.util.UtilitarioSistema;
import br.com.gva.quefominha.domain.dto.customer.CustomerSaveDto;
import br.com.gva.quefominha.domain.dto.customer.CustomerSavedDto;
import br.com.gva.quefominha.domain.entity.Customer;
import br.com.gva.quefominha.domain.enums.Role;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.CustomerRepository;
import br.com.gva.quefominha.service.CustomerService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class CustomerServiceImpl implements CustomerService {

    @Getter
    @Autowired
    private CustomerRepository customerRepository;

    @Getter
    @Autowired
    MongoTemplate mongoOperations;

    @Override
    public <DTO> List<DTO> findAll() {
        return (List<DTO>) getCustomerRepository().findAll();
    }

    @Override
    public <DTO> DTO findById(String id) {
        CustomerSavedDto dto = new CustomerSavedDto();
        Optional<Customer> customer = Optional.of(localFindById(id));
        return (DTO) populateDto(
            customer.orElseThrow(() -> new NegocioException(String.format("Objeto de id %s não encontrado", id))),
            dto
        );
    }

    private Customer localFindById(String id) {
        Optional<Customer> customer = getCustomerRepository().findById(id);
        return customer.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
        Customer customer = new Customer();
        encodePassword(dto);
        return (SAVED) populateDto(
            getCustomerRepository().save(populateEntity(dto, customer)),
            CustomerSavedDto.builder().build()
        );
    }

    /**
     * CORREÇÃO 4: eliminada a dupla escrita no MongoDB.
     *
     * Antes havia dois passos de persistência para o mesmo registro:
     *   1. mongoOperations.findAndModify(...)  → escrita 1
     *   2. customerRepository.save(...)        → escrita 2
     *
     * Isso gerava inconsistência, pois a segunda escrita podia
     * sobrescrever dados da primeira. Agora usamos apenas
     * repository.save() após montar a entidade a partir do DTO,
     * que é o padrão correto e consistente com os demais services.
     *
     * O campo "password" é preservado da entidade existente no banco,
     * pois CustomerUpdateDto não deve carregar senha. Troca de senha
     * ocorre exclusivamente via changePassword().
     */
    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Customer customer = localFindById(id);

        // Preserva a senha atual — update de dados pessoais não altera senha
        String currentPassword = customer.getPassword();
        
        // TODO: Após implementar o "Módulo Administrativo" refazer, pois o admin
        // poderá alterar o "Tipo de Perfil"!
        // Preserva o perfil atual — update de dados pessoais não altera o perfil
        Role currentRole = customer.getRole();

        populateEntity(dto, customer);

        // Define o perfil definido no cadastro
        customer.setRole(currentRole);
        
        // Restaura a senha após o populateEntity para evitar sobrescrita acidental
        customer.setPassword(currentPassword);

        return (SAVED) populateDto(
            getCustomerRepository().save(customer),
            CustomerSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getCustomerRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id) {
        return getCustomerRepository().existsById(id);
    }

    public Boolean checkCurrentPassword(String id, String currentPassword) {
        Customer customer = localFindById(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(currentPassword, customer.getPassword());
    }

    public void changePassword(String id, String newPassword) {
        Customer customer = localFindById(id);
        String encodedPassword = UtilitarioSistema.encodePassword(newPassword);

        Query query = new Query(new Criteria("_id").is(customer.getId()));
        Update update = new Update().set("password", encodedPassword);
        mongoOperations.updateFirst(query, update, "customer");
    }

    private void encodePassword(Object requestDto) {
        String password = ((CustomerSaveDto) requestDto).getPassword();
        String encodedPassword = UtilitarioSistema.encodePassword(password);
        ((CustomerSaveDto) requestDto).setPassword(encodedPassword);
        ((CustomerSaveDto) requestDto).setRole(Role.USER);
    }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO: implementar paginação
        return null;
    }
}
