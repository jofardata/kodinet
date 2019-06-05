package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "gopasses")
public class GoPass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String passenderName;
    private String flightCompany;
    private String airportName;
    private String flightNumber;
    private Double amountPaid;
}
