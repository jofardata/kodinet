package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    @Query(value = "select * from agents order by id desc ", nativeQuery = true)
    @Override
    List<Agent> findAll();

    Agent findByUsername(String username);

    @Query(value = "select count(*) from agents", nativeQuery = true)
    int findCount();

    public List<Agent> findAllByOrderByNameAsc();
}
