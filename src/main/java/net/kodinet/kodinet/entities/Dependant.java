package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "dependants")
public class Dependant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String relation;
    @ManyToOne
    private Person person;
}
