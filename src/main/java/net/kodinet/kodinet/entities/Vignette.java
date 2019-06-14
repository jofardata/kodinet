package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "vignettes")
public class Vignette {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String photo;
    private String agentName;
    private String agentEmail;
    private String phone;
    private String number;
    private String make;
    private String type;
    private String category;
    private String chassis;
    private float amount;
    private String owner;
    private Date createdOn;
    private Date expiresOn;
}
