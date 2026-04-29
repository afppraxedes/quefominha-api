package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.coupon.CouponSavedDto;
import br.com.gva.quefominha.domain.entity.Coupon;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.CouponRepository;
import br.com.gva.quefominha.service.CouponService;
import lombok.Getter;

// CORREÇÃO @SuppressWarnings: removida a supressão em nível de classe.
// Os castings inevitáveis pela assinatura genérica do ServiceUtil
// são marcados pontualmente com @SuppressWarnings("unchecked") inline.
@Service
public class CouponServiceImpl implements CouponService {

    @Getter
    @Autowired
    private CouponRepository couponRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> List<DTO> findAll() {
        CouponSavedDto dto = new CouponSavedDto();
        return getCouponRepository().findAll().stream()
                .map(entity -> (DTO) populateDto(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO> DTO findById(String id) {
        CouponSavedDto dto = new CouponSavedDto();
        Coupon entity = localFindById(id);
        return (DTO) populateDto(entity, dto);
    }

    private Coupon localFindById(String id) {
        Optional<Coupon> result = getCouponRepository().findById(id);
        return result.orElseThrow(
            () -> new NegocioException(String.format("Objeto de id %s não encontrado", id))
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED save(DTO dto) {
        Coupon entity = new Coupon();
        return (SAVED) populateDto(
            getCouponRepository().save(populateEntity(dto, entity)),
            CouponSavedDto.builder().build()
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
        Coupon entity = localFindById(id);
        return (SAVED) populateDto(
            getCouponRepository().save(populateEntity(dto, entity)),
            CouponSavedDto.builder().build()
        );
    }

    @Override
    public void delete(String id) {
        if (existsItem(id)) {
            getCouponRepository().deleteById(id);
        }
    }

    @Override
    public boolean existsItem(String id) {
        return getCouponRepository().existsById(id);
    }

    /**
     * CORREÇÃO findPage: implementação funcional substituindo o retorno null anterior.
     * Usa o método buildPageRequest() herdado de ServiceUtil para padronização.
     * O MongoRepository já oferece findAll(Pageable) nativamente.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        PageRequest pageRequest = buildPageRequest(page, linePerPage, direction, orderBy);
        CouponSavedDto dto = new CouponSavedDto();
        return (Page<DTO>) getCouponRepository().findAll(pageRequest)
                .map(entity -> populateDto(entity, dto));
    }
}
