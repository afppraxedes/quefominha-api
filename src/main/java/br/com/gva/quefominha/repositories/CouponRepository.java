package br.com.gva.quefominha.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gva.quefominha.domain.entity.Coupon;

public interface CouponRepository extends MongoRepository<Coupon, String> {

}
