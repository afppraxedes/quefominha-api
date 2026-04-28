package br.com.gva.quefominha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.gva.quefominha.domain.dto.coupon.CouponSavedDto;
import br.com.gva.quefominha.domain.entity.Coupon;
import br.com.gva.quefominha.exceptions.NegocioException;
import br.com.gva.quefominha.repositories.CouponRepository;
import br.com.gva.quefominha.service.CouponService;
import lombok.Getter;

@SuppressWarnings("unchecked")
@Service
public class CouponServiceImpl implements CouponService {

	@Getter
    @Autowired
    private CouponRepository couponRepository;
	
	@Override
    public <DTO> List<DTO> findAll() {
		CouponSavedDto dto = new CouponSavedDto();
        return getCouponRepository().findAll().stream().map(bag -> (DTO) populateDto(bag, dto)).collect(Collectors.toList());
    }

    @Override
    public <DTO> DTO findById(String id) {
    	CouponSavedDto dto = new CouponSavedDto();
        Optional<Coupon> coupon = Optional.of(localFindById(id));
        return (DTO) populateDto(coupon.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id))), dto);
    }

    private Coupon localFindById(String id){
        Optional<Coupon> coupon = getCouponRepository().findById(id);
        return coupon.orElseThrow(() ->  new NegocioException(String.format("Objeto de id %s não encontrado", id)));
    }

    @Override
    public <DTO, SAVED> SAVED save(DTO dto) {
    	Coupon coupon = new Coupon();
        return (SAVED) populateDto(getCouponRepository().save(populateEntity(dto, coupon)), CouponSavedDto.builder().build());
    }

    @Override
    public <DTO, SAVED> SAVED update(DTO dto, String id) {
    	Coupon coupon = localFindById(id);
        return (SAVED) populateDto(getCouponRepository().save(populateEntity(dto, coupon)), CouponSavedDto.builder().build());
    }

    @Override
    public void delete(String id) {
        if(existsItem(id)){
            getCouponRepository().deleteById(id);
        }
    }

    public boolean existsItem(String id){
        return getCouponRepository().existsById(id);
     }

    @Override
    public <DTO> Page<DTO> findPage(Integer page, Integer linePerPage, String direction, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }
	
}
