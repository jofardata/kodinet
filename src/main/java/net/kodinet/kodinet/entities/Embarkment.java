package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "embarkments")
public class Embarkment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String passengerName;
    private String destination;
    private float amount;
    private String flightCompany;
    private String currency;
    @ManyToOne
    private Agent agent;
    private Date createdOn;
    private String quittance;
    @Column(unique = true)
    private String notefc;
    private String noteusd;
}