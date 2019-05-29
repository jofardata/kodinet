package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "select * from admins where username =?1", nativeQuery = true)
    Admin findByUsername(String username);
}
