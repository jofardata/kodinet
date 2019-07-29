package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.FiscalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FiscalEntityRepository extends JpaRepository<FiscalEntity,Long> {
    List<FiscalEntity> findByEntityType(String entityType);
}
