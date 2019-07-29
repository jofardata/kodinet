package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax,String> {
}
