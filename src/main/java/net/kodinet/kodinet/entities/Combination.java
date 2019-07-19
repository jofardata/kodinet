package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "COMBINATION")
/*@Table(uniqueConstraints={
        @UniqueConstraint(columnNames =
                {"person", "category","seriesfrom","seriesto","vignette","tscr","atbp","cct"})
})*/
public class Combination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String person;
    private int vignette;
    private int tscr;
    private int atbp;
    private int cct;
    private String category;
    private double seriesfrom;
    private double seriesto;
    private String unity;
    private String description;
}
