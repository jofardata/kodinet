package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByBdnIdOrNationalIdOrPhoneOrRfid(String bdn, String nat, String phone, String rfid);

    Person findByBdnIdOrNationalIdOrPhone(String bdn, String nat, String phone);

    @Override
    Page<Person> findAll(Pageable pageable);

    @Query(value = "select count(*) from persons", nativeQuery = true)
    int findCount();
}
