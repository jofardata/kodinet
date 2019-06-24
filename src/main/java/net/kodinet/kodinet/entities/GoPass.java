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

    private String passengerName;
    private float amount;
    private String flightNumber;
    private String currency;

    private String departure;
    private String destination;
    private String genre;
    private String type;
    private Date createdOn;
}
