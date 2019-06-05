package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
