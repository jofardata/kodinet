package net.kodinet.kodinet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    @ManyToOne
    private Entite entite;
    @JsonIgnore
    private boolean hasChangedPassword;
    @ManyToOne
    private Admin createdBy;
}
