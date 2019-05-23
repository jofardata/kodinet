package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
