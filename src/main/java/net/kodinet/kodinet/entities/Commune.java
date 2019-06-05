package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "communes")
public class Commune {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private Town town;
}
