package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Embarkment;
import net.kodinet.kodinet.entities.Vignette;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface EmbarkementRepository extends JpaRepository<Embarkment, Long> {

    @Query(value = "select * from embarkments order by id desc ", nativeQuery = true)
    Page<Embarkment> findPagedData(Pageable pageable);
    @Query(value = "select * from embarkments where created_on between ?1 and ?2", nativeQuery = true)
    Page<Embarkment>findBetweenDates(Date date1, Date date2, Pageable pageable);

}
