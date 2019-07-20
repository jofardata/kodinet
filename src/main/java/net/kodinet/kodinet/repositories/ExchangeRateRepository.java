package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate,Long> {
}
