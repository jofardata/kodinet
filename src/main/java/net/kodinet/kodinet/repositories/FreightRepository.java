package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Freight;
import net.kodinet.kodinet.entities.Vignette;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface FreightRepository extends JpaRepository<Freight, Long> {

    @Query(value = "select * from freights order by id desc ", nativeQuery = true)
    Page<Freight> findPagedData(Pageable pageable);
    @Query(value = "select * from freights where created_on between ?1 and ?2", nativeQuery = true)
    Page<Freight>findBetweenDates(Date date1, Date date2, Pageable pageable);

    @Query(value = "select count(*) from freights", nativeQuery = true)
    int findCount();
}
