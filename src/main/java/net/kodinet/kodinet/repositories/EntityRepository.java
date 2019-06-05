package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Entite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository extends JpaRepository<Entite, Long> {
}
