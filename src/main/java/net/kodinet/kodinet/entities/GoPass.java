package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "gopasses")
public class GoPass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Agent agent;
    @ManyToOne
    private Airline airline;
    @ManyToOne
    private Airport departureAirport;
    @ManyToOne
    private Airport arrivalAirport;
    private Date createdOn;
    private String passenderName;
    private String flightCompany;
    private String airportName;
    private String flightNumber;
    private Double amount;
    private String currency;
}
