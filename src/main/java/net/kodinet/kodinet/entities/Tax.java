package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name="TAX")
@Data
public class Tax {
    @Id
    private String taxId;
    @Column(length = 600)
    private String taxName;
    private String generatingFact;
    private String periodicity;
    @ManyToOne
    private FiscalAdministration fiscalAdministration;
    @ManyToOne
    private Sector sector;

}
