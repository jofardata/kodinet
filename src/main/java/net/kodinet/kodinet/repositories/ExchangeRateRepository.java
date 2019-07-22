package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate,Long> {
    @Query(value = "select * from exchangerate limit 1",nativeQuery = true)
    ExchangeRate findFirst();
}
