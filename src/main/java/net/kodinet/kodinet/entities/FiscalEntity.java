package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name="FISCALENTITY")
@Data
public class FiscalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NAME", length = 250, unique = true, nullable = false)
    private String name;
    @Column(unique = true)
    private String initials;
    private Long center;
    private String entityType;
    @ManyToOne
    private FiscalAdministration fiscalAdministration;
}
