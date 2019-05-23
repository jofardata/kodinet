package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByBdnIdOrNationalIdOrPhoneOrEmail(String input);

    @Override
    Page<Person> findAll(Pageable pageable);
}
