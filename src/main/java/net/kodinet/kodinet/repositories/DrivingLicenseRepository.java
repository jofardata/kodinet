package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.DrivingLicense;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense, Long> {

    @Query(value = "select * from driving_licenses where created_on between " +
            "?1 and ?2 and town =?3", nativeQuery = true)
    Collection<DrivingLicense> customResults(Long start, Long end, String town);

}
