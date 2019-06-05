package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private Town town;
}
