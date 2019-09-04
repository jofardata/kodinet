package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "drivingLicenses")
public class DrivingLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String bdn_id;
    private String photo;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender; //sexe
    private String nationalId;
    private String birthPlace;
    private String bithday;
    private String address;
    private String currency;
    private float amount;
    private String amountString;
    private String phone;
    private String town;
    private String status;
    private String category;
    private Date createdOn;
    private String nationalite;
    private Date dateExp;
    @Column(columnDefinition = "boolean default false")
    private boolean printed;
    private String notefc;
    private String noteusd;
    @Column(unique = true)
    private String quittance;
    private String bdn_id_agent;
    @Column(unique = true)
    private String rfid;
}
