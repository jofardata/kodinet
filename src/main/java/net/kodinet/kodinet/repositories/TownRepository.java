package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TownRepository extends JpaRepository<Town, Long> {

    Collection<Town>findByProvinceId(Long provinceId);
}
