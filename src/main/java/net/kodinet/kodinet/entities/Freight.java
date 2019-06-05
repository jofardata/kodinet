package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "freights")
public class Freight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Agent agent;
    private String email;
    private String phone;
    private String town;
    private String owner;
    private String shippingAgent;
    private Double weight;
    private String freightCompany;
    private Date createdOn;
    private String flightNumber;
    private Airline airline;
    private Double amount;
    private String currency;
    @ManyToOne
    private Airport departureAirport;
    @ManyToOne
    private Airport arrivalAirport;
}
