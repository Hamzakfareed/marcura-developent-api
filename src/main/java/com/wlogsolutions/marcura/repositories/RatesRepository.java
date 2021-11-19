package com.wlogsolutions.marcura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlogsolutions.marcura.models.Rates;

@Repository
public interface RatesRepository extends JpaRepository<Rates, Long> {

	public Rates findByName(String name);

	public Rates findFirstByNameOrderByIdDesc(String name);

	public Rates findByNameAndDate(String from, String date);

}
