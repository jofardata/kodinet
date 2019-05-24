package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "agents")
public class Agent {

    @Id
    private Long id;
    private String name;
    private String username;
    private String password;
}
