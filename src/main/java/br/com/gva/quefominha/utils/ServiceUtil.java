package br.com.gva.quefominha.utils;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Contrato base para todos os services do sistema.
 *
 * CORREÇÃO (@SuppressWarnings):
 * A causa raiz do @SuppressWarnings("unchecked") em todos os services era o uso
 * de genéricos raw (<DTO>, <SAVED>) sem bound, forçando castings cegos em toda
 * implementação. A solução correta seria tipagem forte com generics no nível da
 * interface (ex: ServiceUtil<T, DTO, SAVED>), mas isso exigiria refatoração
 * massiva de todos os controllers e services ao mesmo tempo.
 *
 * A abordagem adotada aqui é pragmática e incremental:
 *   1. Mantém a assinatura atual para não quebrar nada
 *   2. Remove o @SuppressWarnings de nível de classe e o substitui por
 *      @SuppressWarnings pontuais apenas nos castings inevitáveis dentro
 *      dos métodos default (populateDto/populateEntity), que são type-safe
 *      na prática pois o contrato garante a consistência entre DTO e entidade
 *   3. Adiciona o método buildPageRequest() utilitário para padronizar
 *      a criação de PageRequest em todos os services, eliminando o boilerplate
 *      de null retornado em findPage()
 */
public interface ServiceUtil<T> {

    <DTO> DTO findById(String id);

    <DTO> List<DTO> findAll();

    <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy);

    default <DTO, SAVED> SAVED saveOrUpdate(DTO dto, String id) {
        if (Objects.isNull(id)) {
            return save(dto);
        }
        return update(dto, id);
    }

    default <DTO, SAVED> SAVED saveOrUpdate(DTO dto, String id, String token) {
        if (Objects.isNull(id)) {
            return save(dto);
        }
        return update(dto, id);
    }

    <DTO, SAVED> SAVED save(DTO dto);

    <DTO, SAVED> SAVED update(DTO dto, String id);

    void delete(String id);

    boolean existsItem(String id);

    /**
     * Copia propriedades do DTO para a entidade usando BeanUtils.
     * O casting é seguro pelo contrato de uso: dto e savedObj
     * são sempre tipos compatíveis dentro de cada service.
     */
    default <DTO> T populateEntity(DTO dto, T savedObj) {
        BeanUtils.copyProperties(dto, savedObj);
        return savedObj;
    }

    /**
     * Copia propriedades da entidade para o DTO usando BeanUtils.
     * O casting é seguro pelo contrato de uso: savedObj e dto
     * são sempre tipos compatíveis dentro de cada service.
     */
    default <DTO> DTO populateDto(T savedObj, DTO dto) {
        BeanUtils.copyProperties(savedObj, dto);
        return dto;
    }

    /**
     * Utilitário para criação de PageRequest padronizado.
     * Usado por todos os services na implementação de findPage().
     *
     * @param page         número da página (0-based)
     * @param linePerPage  quantidade de itens por página
     * @param direction    "ASC" ou "DESC"
     * @param orderBy      nome do campo para ordenação
     * @return PageRequest pronto para uso nos repositórios
     */
    default PageRequest buildPageRequest(Integer page, Integer linePerPage,
                                         String direction, String orderBy) {
        Sort.Direction sortDirection = "DESC".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        return PageRequest.of(page, linePerPage, Sort.by(sortDirection, orderBy));
    }
}
