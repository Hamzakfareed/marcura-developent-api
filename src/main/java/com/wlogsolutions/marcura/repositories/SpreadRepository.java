package com.wlogsolutions.marcura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlogsolutions.marcura.models.Spread;

@Repository
public interface SpreadRepository extends JpaRepository<Spread, Long> {
	
	public Spread findByCurrencyLike(String name);

}
