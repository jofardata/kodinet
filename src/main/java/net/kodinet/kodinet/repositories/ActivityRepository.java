package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
