package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Embarkment;
import net.kodinet.kodinet.entities.Vignette;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;

public interface EmbarkementRepository extends JpaRepository<Embarkment, Long> {

    @Query(value = "select * from embarkments order by id desc ", nativeQuery = true)
    Page<Embarkment> findPagedData(Pageable pageable);
    @Query(value = "select * from embarkments where created_on between ?1 and ?2", nativeQuery = true)
    Page<Embarkment>findBetweenDates(Date date1, Date date2, Pageable pageable);

    @Query(value = "select count(*) from embarkments", nativeQuery = true)
    int findCount();

    @Query(value = "select * from embarkments where agent_id = ?1", nativeQuery = true)
    Collection<Embarkment> select_embarquement_byIdAgent(Long idAgent);

    @Query(value = "select * from embarkments where noteusd = '535' and agent_id = 4", nativeQuery = true)
    Collection<Embarkment> select_embarquement_noteusd();

    @Query(value = "select count(*) from embarkments where noteusd = '535' and agent_id = 4", nativeQuery = true)
    int findCountnoteusd();
}
