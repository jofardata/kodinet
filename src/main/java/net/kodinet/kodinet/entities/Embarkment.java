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
    @ManyToOne
    private Agent agent;
    private String phone;
    private String Town;
    private String flightCompanyCode;
    private String flightCompany;
    private String passengerName;
    private Double amount;
    private String currency;
    private Date createdOn;
    private Airline airline;
    @ManyToOne
    private Airport departureAirport;
    @ManyToOne
    private Airport arrivalAirport;
}