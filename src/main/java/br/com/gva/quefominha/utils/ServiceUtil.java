package br.com.gva.quefominha.utils;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

public interface ServiceUtil<T>{

    <DTO> DTO findById(String id);

    <DTO> List<DTO> findAll();

    <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy);

    default <DTO, SAVED> SAVED saveOrUpdate(DTO dto, String id){
        if(Objects.isNull(id)){
            return save(dto);
        }        
        return update(dto, id);
    }

    default <DTO, SAVED> SAVED saveOrUpdate(DTO dto, String id, String token){
        if(Objects.isNull(id)){
            return save(dto);
        }
        return update(dto, id);
    }

    <DTO, SAVED> SAVED save(DTO dto);

    <DTO, SAVED> SAVED update(DTO dto, String id);

    void delete(String id);

    default <DTO> T populateEntity(DTO dto, T savedObj) {
        BeanUtils.copyProperties(dto, savedObj);
        return savedObj;
    }

    default <DTO> DTO populateDto(T savedObj, DTO dto){
        BeanUtils.copyProperties(savedObj, dto);
        return dto;
    }

    boolean existsItem(String id);
}