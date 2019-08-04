package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "SECTOR")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private String sectorType;
    @ManyToOne
    private Ministry ministry;
}
