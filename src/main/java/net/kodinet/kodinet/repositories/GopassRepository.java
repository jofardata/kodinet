package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.GoPass;
import net.kodinet.kodinet.entities.Vignette;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface GopassRepository extends JpaRepository<GoPass, Long> {

    @Query(value = "select * from gopasses order by id desc ", nativeQuery = true)
    Page<GoPass> findPagedData(Pageable pageable);
    @Query(value = "select * from gopasses where created_on between ?1 and ?2", nativeQuery = true)
    Page<GoPass>findBetweenDates(Date date1, Date date2, Pageable pageable);
    @Query(value = "select count(*) from gopasses", nativeQuery = true)
    int findCount();
}
