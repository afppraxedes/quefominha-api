package br.com.gva.quefominha.api.v1.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gva.quefominha.domain.dto.coupon.CouponSaveDto;
import br.com.gva.quefominha.domain.dto.coupon.CouponSavedDto;
import br.com.gva.quefominha.domain.dto.coupon.CouponUpdateDto;
import br.com.gva.quefominha.exceptions.ResourceNotFoundException;
import br.com.gva.quefominha.service.CouponService;
import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/api/v1/auth/coupons")
public class CouponController {

	@Getter
	@Autowired
	private CouponService couponService;

	@GetMapping
    public ResponseEntity<List<CouponSavedDto>> findAll() {    
        return ResponseEntity.ok(getCouponService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponSavedDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(getCouponService().findById(id));
    }

    @PostMapping
    public ResponseEntity<CouponSavedDto> save(@RequestBody @Valid CouponSaveDto couponDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(((CouponSavedDto) getCouponService().saveOrUpdate(couponDto, null)).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponSavedDto> update(@PathVariable String id, @RequestBody CouponUpdateDto coupon) {
        getCouponService().saveOrUpdate(coupon, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        getCouponService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
