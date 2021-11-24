package marcura.development.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import marcura.development.api.models.Spread;

@Repository
public interface SpreadRepository extends JpaRepository<Spread, Long> {
	
	public Spread findByCurrencyLike(String name);

}
