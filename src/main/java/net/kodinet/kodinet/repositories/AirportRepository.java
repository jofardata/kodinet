package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
