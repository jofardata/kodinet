package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.DrivingLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense, Long> {
}
