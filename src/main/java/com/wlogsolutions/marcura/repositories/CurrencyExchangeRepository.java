package com.wlogsolutions.marcura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlogsolutions.marcura.models.CurrencyExchange;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

	public CurrencyExchange findByDate(String date);

}
