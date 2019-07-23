package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.DrivingLicense;
import net.kodinet.kodinet.entities.Vignette;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface VignetteRepository extends JpaRepository<Vignette, Long> {


    @Query(value = "select * from vignettes order by id desc ", nativeQuery = true)
    Page<Vignette> findPagedData(Pageable pageable);
    @Query(value = "select * from vignettes where created_on between ?1 and ?2", nativeQuery = true)
    Page<Vignette>findBetweenDates(Date date1, Date date2, Pageable pageable);

    @Query(value = "select count(*) from vignettes", nativeQuery = true)
    int findCount();
}
