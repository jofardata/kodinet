package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
