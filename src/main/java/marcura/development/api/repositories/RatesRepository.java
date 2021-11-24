package marcura.development.api.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import marcura.development.api.models.Rates;

@Repository
public interface RatesRepository extends JpaRepository<Rates, Long> {

	public Rates findByName(String name);

	public Rates findFirstByNameOrderByIdDesc(String name);

	public Rates findByNameAndDate(String from, String date);

	public ArrayList<Rates> findAllByCurrencyExchangeId(long id);

}
