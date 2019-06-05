package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Country, Long> {
}
