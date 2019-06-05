package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Country;
import net.kodinet.kodinet.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Collection<Province>findByCountryId(Long countryId);
}
