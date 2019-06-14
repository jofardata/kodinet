package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "drivingLicenses")
public class DrivingLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String photo;
    private String name;
    private String email;
    private String phone;
    private String town;
    private String status;
    private String category;
    private Date createdOn;
}
