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
    private String passengerName;
    private String phone;
    private String town;
    private String destination;
    private float weight;
    private float amount;
    private String currency;
    private Date createdOn;

}
