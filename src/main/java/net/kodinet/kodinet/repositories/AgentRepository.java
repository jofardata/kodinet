package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Agent findByUsername(String username);
}
